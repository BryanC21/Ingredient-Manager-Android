package com.sjsu.hackathon.ingredient_manager.data.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {
    private int id;
    private String name;
    private int servings;
    private ArrayList<RecipeIngredient> ingredientList;
    private ArrayList<String> instructionList;
    private boolean saved;
    private JSONObject json;


    public Recipe(String name, int servings, ArrayList<RecipeIngredient> ingredientList,
                  ArrayList<String> instructionList, JSONObject json, boolean saved) {
        this.name = name;
        this.servings = servings;
        this.ingredientList = ingredientList;
        this.instructionList = instructionList;
        this.json = json;
        this.saved = saved;
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
        if (recipeJson.has("saved")) {
            this.saved = recipeJson.getBoolean("saved");
        } else {
            this.saved = false;
            recipeJson.put("saved", false);
        }
        this.json = recipeJson;
    }

    public Recipe(JSONObject recipeJson, int id) throws JSONException {
        this(recipeJson);
        this.id = id;
    }

    protected Recipe(Parcel in) {
        name = in.readString();
        servings = in.readInt();
        ingredientList = in.createTypedArrayList(RecipeIngredient.CREATOR);
        instructionList = in.createStringArrayList();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        JSONObject recipeJson = new JSONObject();
        try {
            recipeJson.put("id", this.id);
            recipeJson.put("name", this.name);
            recipeJson.put("servings", this.servings);
            JSONArray ingredientList = new JSONArray();
            for (RecipeIngredient recipeIngredient : this.ingredientList) {
                ingredientList.put(recipeIngredient.toJson());
            }
            recipeJson.put("ingredients", ingredientList);
            JSONArray instructionList = new JSONArray();
            for (String instruction : this.instructionList) {
                instructionList.put(instruction);
            }
            recipeJson.put("instructions", instructionList);
        } catch (Exception e) {
            Log.e("Json fail", e.getMessage());
        }
        return recipeJson;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public void setSaved() {
        this.saved = !this.saved;
    }

    public String toString() {
        return "Recipe: " + name + " " + " " + servings + " " + saved;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(servings);
        parcel.writeList(ingredientList);
        parcel.writeStringList(instructionList);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            parcel.writeBoolean(saved);
        }
    }
}
