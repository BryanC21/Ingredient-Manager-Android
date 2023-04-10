package com.sjsu.hackathon.ingredient_manager.data.listener;

import com.sjsu.hackathon.ingredient_manager.data.model.Category;
import com.sjsu.hackathon.ingredient_manager.data.model.Ingredient;
import com.sjsu.hackathon.ingredient_manager.data.model.Location;
import com.sjsu.hackathon.ingredient_manager.data.model.Unit;

import java.util.ArrayList;

public interface DataListener {
    void onDataSuccess(String reason);
    void onDataFail(String reason);
}
