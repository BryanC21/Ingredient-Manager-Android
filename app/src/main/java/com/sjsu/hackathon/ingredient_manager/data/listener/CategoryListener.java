package com.sjsu.hackathon.ingredient_manager.data.listener;

import com.sjsu.hackathon.ingredient_manager.data.model.Category;

import java.util.ArrayList;

public interface CategoryListener extends DataListener {
    void onCategoryGetAllFinish(ArrayList<Category> dataList);
    void onCategoryGetFinish(Category data);
}
