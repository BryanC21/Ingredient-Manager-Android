package com.sjsu.hackathon.ingredient_manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.hackathon.ingredient_manager.data.model.Recipe;
import com.sjsu.hackathon.ingredient_manager.data.model.RecipeIngredient;
import com.sjsu.hackathon.ingredient_manager.databinding.FragmentRecipeDetailsBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link recipe_details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class recipe_details extends Fragment {

    private FragmentRecipeDetailsBinding binding;
    private View rootView;

    private NavController navController;

    public recipe_details() {
        // Required empty public constructor
    }
    public static recipe_details newInstance() {
        recipe_details fragment = new recipe_details();
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            navController.popBackStack(); // Navigate back to previous fragment
            return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_ingredient_details, container, false);

        //---------
        if (getArguments() != null) {
            Recipe recipe = getArguments().getParcelable("recipe");

            // Name
            TextView name = rootView.findViewById(R.id.ingredient_details_name);
            name.setText("Name: " + recipe.getName());

            // servings
            TextView servings = rootView.findViewById(R.id.ingredient_details_quantity);
            servings.setText("Servings: " + recipe.getServings());

            try {
                RecyclerView recyclerView = binding.ingredientList;
                RecipeIngredientListAdapter adapter = new RecipeIngredientListAdapter(recipe.getIngredientList());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adapter);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        //---------

        return rootView;
    }


}