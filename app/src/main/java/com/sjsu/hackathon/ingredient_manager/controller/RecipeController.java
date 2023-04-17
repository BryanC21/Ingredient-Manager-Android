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

    public RecipeController(Context context, RecipeListener recipeListener) {
        this.context = context;
        this.recipeListener = recipeListener;
    }

    public void getRecipe(ArrayList<Ingredient> list, int number) {
        Chatgpt chatgpt = new Chatgpt(this.context);
        String message = "Create a recipe list in JSONArray format containing " + String.valueOf(number) +
                " recipes in this format {'name', 'servings', 'ingredients':[{'name', 'quantity', " +
                "'preparation'}], 'instructions': []} using ";
        for (Ingredient ingredient : list) {
            message += ingredient.getName() + ", ";
        }
        chatgpt.sendMessage(message, this);
    }

    @Override
    public void onSuccess(String response) {
        try {
            System.out.println(response);
            ArrayList<Recipe> recipeList = new ArrayList<>();
            JSONObject result = new JSONObject(response);
            String recipeText = result.getJSONArray("choices").getJSONObject(0)
                    .getJSONObject("message").getString("content");
//            System.out.println(recipeText);
            JSONArray recipeListJson = new JSONArray(recipeText.split("\n\n")[1]);
            for (int i = 0; i < recipeListJson.length(); i++) {
                JSONObject recipeJson = recipeListJson.getJSONObject(i);
                ArrayList<RecipeIngredient> ingredientList = new ArrayList<>();
                JSONArray ingredientListJson = recipeJson.getJSONArray("ingredients");
                for (int j = 0; j < ingredientListJson.length(); j++) {
                    JSONObject recipeIngredientJson = ingredientListJson.getJSONObject(j);
                    RecipeIngredient recipeIngredient = new RecipeIngredient(
                            recipeIngredientJson.getString("name"),
                            recipeIngredientJson.getString("quantity"),
                            recipeIngredientJson.getString("preparation"));
                    ingredientList.add(recipeIngredient);
                }

                ArrayList<String> instructionList = new ArrayList<>();
                JSONArray instructionListJson = recipeJson.getJSONArray("instructions");
                for (int j = 0; j < instructionListJson.length(); j++) {
                    instructionList.add(instructionListJson.getString(j));
                }
                Recipe recipe = new Recipe(recipeJson.getString("name"),
                    recipeJson.getInt("servings"), ingredientList, instructionList);
                recipeList.add(recipe);
            }
            recipeListener.onGetSuccess(recipeList);
        } catch (JSONException error) {
            recipeListener.onDataFail(error.getMessage());
            Log.e("Recipe Json Parse Error", error.getMessage());
        }
    }

    @Override
    public void onError() {
        recipeListener.onDataFail("Get recipe Failed");
    }
}
