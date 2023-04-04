package com.sjsu.hackathon.ingredient_manager;

import com.sjsu.hackathon.ingredient_manager.models.Category;
import com.sjsu.hackathon.ingredient_manager.models.Ingredient;
import com.sjsu.hackathon.ingredient_manager.models.Location;
import com.sjsu.hackathon.ingredient_manager.models.Unit;

import java.util.ArrayList;

public interface DataListener {
    void onIngredientDataFinish(ArrayList<Ingredient> dataList);
    void onLocationDataFinish(ArrayList<Location> dataList);
    void onUnitDataFinish(ArrayList<Unit> dataList);
    void onCategoryDataFinish(ArrayList<Category> dataList);
    void onDataFail(String reason);
}
