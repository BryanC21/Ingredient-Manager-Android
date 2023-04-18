package com.sjsu.hackathon.ingredient_manager.data.listener;

import com.sjsu.hackathon.ingredient_manager.data.model.Category;

import java.util.ArrayList;

public interface UserListener extends DataListener {
    void onExistsFinish(boolean exists);
}
