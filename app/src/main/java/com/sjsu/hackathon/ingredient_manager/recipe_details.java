package com.sjsu.hackathon.ingredient_manager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sjsu.hackathon.ingredient_manager.data.handler.RecipeHandler;
import com.sjsu.hackathon.ingredient_manager.data.model.Recipe;
import com.sjsu.hackathon.ingredient_manager.data.model.RecipeIngredientListAdapter;
import com.sjsu.hackathon.ingredient_manager.data.model.RecipeInstructionListAdapter;
import com.sjsu.hackathon.ingredient_manager.databinding.FragmentRecipeDetailsBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link recipe_details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class recipe_details extends Fragment {

    private FragmentRecipeDetailsBinding binding;
    private View rootView;

    private NavController navController;

    private BottomNavigationView bottomNavigationView;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        navController.popBackStack(); // Navigate back to previous fragment
            return true;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);
        bottomNavigationView = getActivity().findViewById(R.id.nav_view);

        Log.d("RecipeDetails", bottomNavigationView.toString());

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.fragment_recipe_details) {
                bottomNavigationView.setVisibility(View.GONE);
            } else {
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_recipe_details, container, false);

        // Bottom Navigation
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);

        //---------
        if (getArguments() != null) {
            Recipe recipe = getArguments().getParcelable("recipe");
            System.out.println(recipe);
            // Name
            TextView name = rootView.findViewById(R.id.recipe_details_name);
            name.setText("Name: " + recipe.getName());

            // servings
            TextView servings = rootView.findViewById(R.id.recipe_details_servings);
            servings.setText("Servings: " + recipe.getServings());

            RecyclerView.ItemDecoration itemDecoration = new
                    DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);

            RecyclerView ingredientListView = rootView.findViewById(R.id.ingredientListView);
            ingredientListView.addItemDecoration(itemDecoration);
            ingredientListView.setAdapter(new RecipeIngredientListAdapter(recipe.getIngredientList()));
            ingredientListView.setLayoutManager(new LinearLayoutManager(this.getContext()));

            RecyclerView instructionListView = rootView.findViewById(R.id.instructionListView);
            instructionListView.addItemDecoration(itemDecoration);
            instructionListView.setAdapter(new RecipeInstructionListAdapter(recipe.getInstructionList()));
            instructionListView.setLayoutManager(new LinearLayoutManager(this.getContext()));


            Button button = rootView.findViewById(R.id.recipe_details_save_btn);
            if (recipe.isSaved()) {
                button.setText("Unsave");
            } else {
                button.setText("Save");
            }
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    RecipeHandler recipeHandler = new RecipeHandler(getActivity());
                    if (recipe.isSaved()) {
                        recipe.setSaved();
                        recipeHandler.removeData(recipe.getId());
                        button.setText("Save");
                    } else {
                        recipe.setSaved();
                        recipeHandler.addNewData(recipe);
                        button.setText("Unsave");
                    }
                }
            });
        }
        //---------

        return rootView;
    }

}