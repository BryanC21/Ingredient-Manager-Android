package com.sjsu.hackathon.ingredient_manager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sjsu.hackathon.ingredient_manager.data.model.Recipe;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeCell extends RecyclerView.ViewHolder {

    public TextView titleTextView;

    public Recipe recipe;

    public RecipeCell(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.title_text_view3);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for this cell
                // Navigate to a new fragment
                Bundle bundle = new Bundle();

                bundle.putParcelable("recipe", recipe);

                NavController navController = Navigation.findNavController(v);

                NavOptions navOptions = new NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setPopUpTo(navController.getCurrentDestination().getId(), false)
                        .build();

                navController.navigate(R.id.action_fragment_recipe_list_to_fragment_recipe_details, bundle, navOptions);

            }
        });

    }
}
