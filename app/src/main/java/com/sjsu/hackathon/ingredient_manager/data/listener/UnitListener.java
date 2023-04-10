package com.sjsu.hackathon.ingredient_manager.data.listener;

import com.sjsu.hackathon.ingredient_manager.data.model.Unit;

import java.util.ArrayList;

public interface UnitListener extends DataListener {
    void onGetAllFinish(ArrayList<Unit> dataList);
    void onGetFinish(Unit data);
}
