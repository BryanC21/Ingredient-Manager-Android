package com.sjsu.hackathon.ingredient_manager.data.listener;

import com.sjsu.hackathon.ingredient_manager.data.model.Recipe;

import java.util.ArrayList;

public interface RecipeListener extends DataListener {
    void onGetSuccess(ArrayList<Recipe> recipeList);
}
