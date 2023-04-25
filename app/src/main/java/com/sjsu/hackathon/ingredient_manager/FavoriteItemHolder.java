package com.sjsu.hackathon.ingredient_manager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.hackathon.ingredient_manager.data.model.Recipe;

public class FavoriteItemHolder extends RecyclerView.ViewHolder {
    private TextView recipeNameText;
    private Recipe recipe;

    public FavoriteItemHolder(final View itemView) {
        super(itemView);
        recipeNameText = itemView.findViewById(R.id.favorite_recipe_name);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for this cell
                // Navigate to a new fragment
                Bundle bundle = new Bundle();
                bundle.putParcelable("recipe", recipe);
                recipe_details r = new recipe_details();
                r.setArguments(bundle);
                NavController navController = Navigation.findNavController(v);

                NavOptions navOptions = new NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setPopUpTo(navController.getCurrentDestination().getId(), false)
                        .build();

                navController.navigate(R.id.fragment_recipe_details, bundle, navOptions);
            }
        });
    }

    public void setRecipeNameText(String text) {
        this.recipeNameText.setText(text);
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}