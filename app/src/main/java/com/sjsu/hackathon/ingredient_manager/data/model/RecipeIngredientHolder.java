package com.sjsu.hackathon.ingredient_manager.data.model;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.hackathon.ingredient_manager.R;

public class RecipeIngredientHolder extends RecyclerView.ViewHolder {
    private TextView recipe_ingredient_name;
    private TextView recipe_ingredient_quantity;
    private TextView recipe_ingredient_preparation;

    public RecipeIngredientHolder(final View itemView) {
        super(itemView);
        this.recipe_ingredient_name = itemView.findViewById(R.id.recipe_ingredient_name);
        this.recipe_ingredient_quantity = itemView.findViewById(R.id.recipe_ingredient_quantity);
        this.recipe_ingredient_preparation = itemView.findViewById(R.id.recipe_ingredient_preparation);
        System.out.println(this.recipe_ingredient_name);
        System.out.println(this.recipe_ingredient_quantity);
        System.out.println(this.recipe_ingredient_preparation);
    }

    public void set(String name, String quantity, String preparation) {
        this.recipe_ingredient_name.setText(name);
        this.recipe_ingredient_quantity.setText(quantity);
        this.recipe_ingredient_preparation.setText(preparation);
    }
}
