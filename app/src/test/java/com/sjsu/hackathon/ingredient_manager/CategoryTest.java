package com.sjsu.hackathon.ingredient_manager;

import org.junit.Test;

import static org.junit.Assert.*;

import com.sjsu.hackathon.ingredient_manager.data.handler.CategoryHandler;
import com.sjsu.hackathon.ingredient_manager.data.listener.CategoryListener;
import com.sjsu.hackathon.ingredient_manager.data.model.Category;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CategoryTest implements CategoryListener {
    @Test
    public void test() {
        CategoryHandler categoryHandler = new CategoryHandler(this);
        categoryHandler.add("test1");
        categoryHandler.getAll();
    }

    @Override
    public void onCategoryGetAllFinish(ArrayList<Category> dataList) {
        String[] result = {"", "Fruit", "Meat", "Seafood", "Vegetable"};
        assertEquals(dataList.size(), result.length);
        for (int i = 0; i < dataList.size(); i++) {
            assertEquals(dataList.get(i).getName(), result[i]);
        }
    }

    @Override
    public void onCategoryGetFinish(Category data) {
        assertEquals(data.getName(), "Fruit");
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