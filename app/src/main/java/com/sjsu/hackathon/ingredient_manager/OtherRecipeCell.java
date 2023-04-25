package com.sjsu.hackathon.ingredient_manager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.hackathon.ingredient_manager.data.model.RecipeIngredient;

import java.util.Date;

public class OtherRecipeCell extends RecyclerView.ViewHolder {

    public TextView nameTextView;
    public TextView quantityTextView;
    public TextView preparationTextView;

    private RecipeIngredient recipeIngredient;

    public void setDetails(RecipeIngredient recipeIngredient) {
        this.recipeIngredient = recipeIngredient;
    }

    public OtherRecipeCell(@NonNull View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.recipe_ingredient_name);
        quantityTextView = itemView.findViewById(R.id.recipe_ingredient_quantity);
        preparationTextView = itemView.findViewById(R.id.recipe_ingredient_preparation);
    }
}