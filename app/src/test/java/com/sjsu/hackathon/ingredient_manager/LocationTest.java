package com.sjsu.hackathon.ingredient_manager;

import static org.junit.Assert.assertEquals;

import com.sjsu.hackathon.ingredient_manager.data.handler.LocationHandler;
import com.sjsu.hackathon.ingredient_manager.data.handler.UnitHandler;
import com.sjsu.hackathon.ingredient_manager.data.listener.LocationListener;
import com.sjsu.hackathon.ingredient_manager.data.listener.UnitListener;
import com.sjsu.hackathon.ingredient_manager.data.model.Location;
import com.sjsu.hackathon.ingredient_manager.data.model.Unit;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LocationTest implements LocationListener {
    @Test
    public void test() {
        LocationHandler handler = new LocationHandler(this);
        handler.add("test1");
        handler.getAll();
    }

    @Override
    public void onLocationGetAllFinish(ArrayList<Location> dataList) {
        String[] result = {"", "Fridge", "Counter"};
        assertEquals(dataList.size(), result.length);
        for (int i = 0; i < dataList.size(); i++) {
            assertEquals(dataList.get(i).getName(), result[i]);
        }
    }

    @Override
    public void onLocationGetFinish(Location data) {
        assertEquals(data.getName(), "Fridge");
    }

    @Override
    public void onDataSuccess(String reason) {
        assertEquals(reason, "success");
    }

    @Override
    public void onDataFail(String reason) {
        assertEquals(reason, "fail");
    }
}