package com.sjsu.hackathon.ingredient_manager;

import androidx.annotation.NonNull;

import com.sjsu.hackathon.ingredient_manager.data.model.Recipe;

public class FavoriteItem {
    private Recipe recipe;

    public FavoriteItem(@NonNull final Recipe recipe) {
        this.recipe = recipe;
    }

    @NonNull
    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(@NonNull final Recipe recipe) {
        this.recipe = recipe;
    }
}