package com.sjsu.hackathon.ingredient_manager.data.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private int servings;
    private ArrayList<RecipeIngredient> ingredientList;
    private ArrayList<String> instructionList;
    private JSONObject json;

    public Recipe(String name, int servings, ArrayList<RecipeIngredient> ingredientList,
                  ArrayList<String> instructionList, JSONObject json) {
        this.name = name;
        this.servings = servings;
        this.ingredientList = ingredientList;
        this.instructionList = instructionList;
        this.json = json;
    }

    public Recipe(JSONObject recipeJson) throws JSONException {
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
        this.name = recipeJson.getString("name");
        this.servings = recipeJson.getInt("servings");
        this.ingredientList = ingredientList;
        this.instructionList = instructionList;
        this.json = recipeJson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public ArrayList<RecipeIngredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(ArrayList<RecipeIngredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public ArrayList<String> getInstructionList() {
        return instructionList;
    }

    public void setInstructionList(ArrayList<String> instructionList) {
        this.instructionList = instructionList;
    }

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    public String toString() {
        return "Recipe: " + name + " " + " " + servings;
    }

}
