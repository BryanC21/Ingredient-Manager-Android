package com.sjsu.hackathon.ingredient_manager;

import static org.junit.Assert.assertEquals;

import com.sjsu.hackathon.ingredient_manager.data.handler.CategoryHandler;
import com.sjsu.hackathon.ingredient_manager.data.handler.UnitHandler;
import com.sjsu.hackathon.ingredient_manager.data.listener.CategoryListener;
import com.sjsu.hackathon.ingredient_manager.data.listener.UnitListener;
import com.sjsu.hackathon.ingredient_manager.data.model.Category;
import com.sjsu.hackathon.ingredient_manager.data.model.Unit;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest implements UnitListener {
    @Test
    public void test() {
        UnitHandler handler = new UnitHandler(this);
        handler.add("test1");
        handler.getAll();
    }

    @Override
    public void onUnitGetAllFinish(ArrayList<Unit> dataList) {
        String[] result = {"Count", "Pound", "Bottle", "Dozen"};
        assertEquals(dataList.size(), result.length);
        for (int i = 0; i < dataList.size(); i++) {
            assertEquals(dataList.get(i).getName(), result[i]);
        }
    }

    @Override
    public void onUnitGetFinish(Unit data) {
        assertEquals(data.getName(), "Count");
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