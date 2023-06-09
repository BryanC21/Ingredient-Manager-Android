package com.sjsu.hackathon.ingredient_manager.controller;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonParser;
import com.sjsu.hackathon.ingredient_manager.data.listener.ChatgptListener;
import com.sjsu.hackathon.ingredient_manager.data.listener.RecipeListener;
import com.sjsu.hackathon.ingredient_manager.data.model.Ingredient;
import com.sjsu.hackathon.ingredient_manager.data.model.Recipe;
import com.sjsu.hackathon.ingredient_manager.data.model.RecipeIngredient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeController implements ChatgptListener {

    private Context context;
    private RecipeListener recipeListener;
    private ArrayList<Ingredient> list;

    private String cuisine;
    private int number;
    private int retry;

    private JSONArray messages;

    private Chatgpt chatgpt;

    public RecipeController(Context context, RecipeListener recipeListener) {
        this.context = context;
        this.recipeListener = recipeListener;
        this.retry = 3;
        chatgpt = new Chatgpt(this.context);
        messages = new JSONArray();
    }

    public void getRecipe(ArrayList<Ingredient> list, int number, String cuisine) {
        this.list = list;
        this.number = number;
        this.cuisine = cuisine;
        String message = "Create a new recipe list in JSONArray format containing " +
                String.valueOf(number) + " recipes in this format {'name', 'servings', " +
                "'ingredients':[{'name', 'quantity', " +
                "'preparation'}], 'instructions': []} ";
        if (cuisine != "") {
            message += " in " + cuisine + " cuisine ";
        }
        message += " using ";
        for (Ingredient ingredient : list) {
            message += ingredient.getName() + ", ";
        }
        message += ". assume i have all condiments. avoid any prompt prefixes and give the response directly";
        chatgpt.sendMessage(message, this, messages);
    }

    public void clearHistory() {
        messages = new JSONArray();
    }

    @Override
    public void onSuccess(String response) {
        try {
            System.out.println(response);
            ArrayList<Recipe> recipeList = new ArrayList<>();
            JSONObject result = new JSONObject(response);
            JSONObject messageJSON = result.getJSONArray("choices").getJSONObject(0)
                    .getJSONObject("message");
            messages.put(messageJSON);

            String recipeText = messageJSON.getString("content");
            recipeText = recipeText.substring(recipeText.indexOf('['), recipeText.lastIndexOf(']') + 1);
            JSONArray recipeListJson = new JSONArray(recipeText);
            for (int i = 0; i < recipeListJson.length(); i++) {
                JSONObject recipeJson = recipeListJson.getJSONObject(i);

                Recipe recipe = new Recipe(recipeJson);
                recipeList.add(recipe);
            }
            messages.put(messageJSON);
            recipeListener.onGetSuccess(recipeList);
        } catch (JSONException | ArrayIndexOutOfBoundsException error) {
            Log.e("Recipe Json Parse Error", error.getMessage());

            if (retry-- > 0) {
                Log.e("Retrying Chatgpt", "Retry Left: " + Integer.toString(retry));
                getRecipe(list, number, cuisine);
            } else {
                recipeListener.onDataFail(error.getMessage());
            }
        }
    }

    @Override
    public void onError() {
        recipeListener.onDataFail("Get recipe Failed");
    }
}
