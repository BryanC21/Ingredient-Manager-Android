package com.sjsu.hackathon.ingredient_manager;

import java.util.ArrayList;

public interface DataListener {
    void onDataFinish(ArrayList<Data> dataList);
    void onDataFail(String reason);
}
