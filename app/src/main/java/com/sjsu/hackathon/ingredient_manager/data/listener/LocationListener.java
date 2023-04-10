package com.sjsu.hackathon.ingredient_manager.data.listener;

import com.sjsu.hackathon.ingredient_manager.data.model.Location;

import java.util.ArrayList;

public interface LocationListener extends DataListener {
    void onGetAllFinish(ArrayList<Location> dataList);
    void onGetFinish(Location data);
}
