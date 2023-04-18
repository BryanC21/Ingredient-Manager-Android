package com.sjsu.hackathon.ingredient_manager.data.listener;

import com.sjsu.hackathon.ingredient_manager.data.model.Ingredient;

import java.util.ArrayList;

public interface IngredientListener extends DataListener {
    void onIngredientGetAllFinish(ArrayList<Ingredient> dataList);
    void onIngredientGetFinish(Ingredient data);
}
