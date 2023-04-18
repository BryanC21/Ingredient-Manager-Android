package com.sjsu.hackathon.ingredient_manager.data.listener;

import com.sjsu.hackathon.ingredient_manager.data.model.Unit;

import java.util.ArrayList;

public interface UnitListener extends DataListener {
    void onUnitGetAllFinish(ArrayList<Unit> dataList);
    void onUnitGetFinish(Unit data);
}
