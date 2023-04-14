package com.sjsu.hackathon.ingredient_manager.controller;

import android.content.Context;

import com.sjsu.hackathon.ingredient_manager.data.listener.ChatgptListener;
import com.sjsu.hackathon.ingredient_manager.data.model.Ingredient;

import java.util.ArrayList;

public class RecipeController implements ChatgptListener {

    private Context context;

    public RecipeController(Context context) {
        this.context = context;
    }

    public void getRecipe(ArrayList<Ingredient> list, int number) {
        Chatgpt chatgpt = new Chatgpt(this.context);
        String message = "Create 1 JSON list containing " + String.valueOf(number) +
                " recipes in JSON format [{'name', 'servings', 'ingredients':[{'name', 'quantity', " +
                "'preparation'}], 'instructions': []}] using ";
        for (Ingredient ingredient : list) {
            message += ingredient.getName() + ", ";
        }
        chatgpt.sendMessage(message, this);
    }

    @Override
    public void onSuccess(String response) {
        System.out.println(response);
    }

    @Override
    public void onError() {

    }
}
