package com.sjsu.hackathon.ingredient_manager.data.model;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private int servings;
    private ArrayList<RecipeIngredient> ingredientList;
    private ArrayList<String> instructionList;

    public Recipe(String name, int servings, ArrayList<RecipeIngredient> ingredientList, ArrayList<String> instructionList) {
        this.name = name;
        this.servings = servings;
        this.ingredientList = ingredientList;
        this.instructionList = instructionList;
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

    public String toString() {
        return "Recipe: " + name + " " + " " + servings;
    }
}
