package com.sjsu.hackathon.ingredient_manager.controller;

import com.sjsu.hackathon.ingredient_manager.data.handler.CategoryHandler;
import com.sjsu.hackathon.ingredient_manager.data.handler.LocationHandler;
import com.sjsu.hackathon.ingredient_manager.data.handler.UnitHandler;
import com.sjsu.hackathon.ingredient_manager.data.listener.CategoryListener;
import com.sjsu.hackathon.ingredient_manager.data.listener.LocationListener;
import com.sjsu.hackathon.ingredient_manager.data.listener.UnitListener;
import com.sjsu.hackathon.ingredient_manager.data.model.Category;
import com.sjsu.hackathon.ingredient_manager.data.model.Location;
import com.sjsu.hackathon.ingredient_manager.data.model.Unit;

import java.util.ArrayList;

public class AddDefaultController  implements UnitListener, LocationListener, CategoryListener {

    public AddDefaultController() {

    }

    public void addDefault() {
        UnitHandler unitHandler = new UnitHandler(this);
        unitHandler.add(" ");
        unitHandler.add("Count");
        unitHandler.add("Pound");
        unitHandler.add("Bottle");
        unitHandler.add("Dozen");

        LocationHandler locationHandler = new LocationHandler(this);
        locationHandler.add(" ");
        locationHandler.add("Fridge");
        locationHandler.add("Counter");

        CategoryHandler categoryHandler = new CategoryHandler(this);
        categoryHandler.add(" ");
        categoryHandler.add("Fruit");
        categoryHandler.add("Meat");
        categoryHandler.add("Seafood");
        categoryHandler.add("Vegetable");
    }

    @Override
    public void onCategoryGetAllFinish(ArrayList<Category> dataList) {

    }

    @Override
    public void onCategoryGetFinish(Category data) {

    }

    @Override
    public void onDataSuccess(String reason) {

    }

    @Override
    public void onDataFail(String reason) {

    }

    @Override
    public void onLocationGetAllFinish(ArrayList<Location> dataList) {

    }

    @Override
    public void onLocationGetFinish(Location data) {

    }

    @Override
    public void onUnitGetAllFinish(ArrayList<Unit> dataList) {

    }

    @Override
    public void onUnitGetFinish(Unit data) {

    }
}


