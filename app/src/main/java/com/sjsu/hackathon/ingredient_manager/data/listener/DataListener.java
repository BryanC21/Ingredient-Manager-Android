package com.sjsu.hackathon.ingredient_manager.data.listener;

import com.sjsu.hackathon.ingredient_manager.data.model.Unit;

public interface DataListener {
    void onDataSuccess(String reason);
    void onDataFail(String reason);
}
