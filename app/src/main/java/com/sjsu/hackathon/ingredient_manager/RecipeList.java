package com.sjsu.hackathon.ingredient_manager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.sjsu.hackathon.ingredient_manager.controller.RecipeController;
import com.sjsu.hackathon.ingredient_manager.data.listener.RecipeListener;
import com.sjsu.hackathon.ingredient_manager.data.model.Ingredient;
import com.sjsu.hackathon.ingredient_manager.data.model.Recipe;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeList extends Fragment implements RecipeListener {

    private View rootView;
    private NavController navController;

    private ArrayList<Recipe> recipeList;

    private ArrayList<Ingredient> ingredientList;

    private RecipeController recipeController;

    private Button regen;

    public RecipeList() {
        // Required empty public constructor
    }
    public static RecipeList newInstance() {
        RecipeList fragment = new RecipeList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);

        regen = rootView.findViewById(R.id.regen_button);
        regen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("regen", "beginning regen");
                rootView.findViewById(R.id.notifications_loading2).setVisibility(View.VISIBLE);
                recipeController.getRecipe(ingredientList, 2);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView =  inflater.inflate(R.layout.fragment_recipe_list, container, false);

        if (getArguments() != null) {

            //recipeList = getArguments().getParcelableArrayList("recipes");
            ArrayList<String> ingredientsTemp = getArguments().getStringArrayList("ingredients");
            ingredientList = new ArrayList<>();
            recipeList = new ArrayList<>();

            for(int i = 0; i < ingredientsTemp.size(); i++){
                //TODO very bad, fix this, string to ingredient
                ingredientList.add(new Ingredient(ingredientsTemp.get(i), 1.0, "none", "none", new Date(), new Date(), "none", "none", "none"));
            }

            recipeController = new RecipeController(getContext(), this);
            Log.d("Attempt GPT", "starting");
            rootView.findViewById(R.id.notifications_loading2).setVisibility(View.VISIBLE);
            recipeController.getRecipe(ingredientList, 2);
        }

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        navController.popBackStack(); // Navigate back to previous fragment
        return true;
    }

    @Override
    public void onDataSuccess(String reason) {

    }

    @Override
    public void onDataFail(String reason) {
        rootView.findViewById(R.id.notifications_loading2).setVisibility(View.INVISIBLE);
        Toast.makeText(getContext(), "Generate Recipes Failed. Try again.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGetSuccess(ArrayList<Recipe> recipeList) {
        //this.recipeList.addAll(recipeList);
        Log.d("RecipeList", "count: " + recipeList.size());
        Log.d("RecipeListGlobal", "count: " + this.recipeList.size());
        rootView.findViewById(R.id.notifications_loading2).setVisibility(View.INVISIBLE);
        for(Recipe r : recipeList){
            this.recipeList.add(r);
        }
        //recipeController.clearHistory();
        RecyclerView recyclerView = rootView.findViewById(R.id.recipe_list_recycler_view);
        RecipeListAdapter adapter = new RecipeListAdapter(this.recipeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}