package com.sjsu.hackathon.ingredient_manager;

import static org.junit.Assert.assertEquals;

import com.sjsu.hackathon.ingredient_manager.data.handler.IngredientHandler;
import com.sjsu.hackathon.ingredient_manager.data.handler.UnitHandler;
import com.sjsu.hackathon.ingredient_manager.data.listener.IngredientListener;
import com.sjsu.hackathon.ingredient_manager.data.listener.UnitListener;
import com.sjsu.hackathon.ingredient_manager.data.model.Ingredient;
import com.sjsu.hackathon.ingredient_manager.data.model.Unit;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class IngredientTest implements IngredientListener {
    @Test
    public void test() {
        IngredientHandler handler = new IngredientHandler(this);
        handler.add("test1", 1, "", "test1", new Date(), new Date(), "test1", "test1", "test1");
        handler.getAll();
    }

    @Override
    public void onIngredientGetAllFinish(ArrayList<Ingredient> dataList) {
        String[] result = {"Tomato", "Potato", "Milk", "Egg"};
        assertEquals(dataList.size(), result.length);
        for (int i = 0; i < dataList.size(); i++) {
            assertEquals(dataList.get(i).getName(), result[i]);
        }
    }

    @Override
    public void onIngredientGetFinish(Ingredient data) {
        assertEquals(data.getName(), "Tomato");
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