package com.sjsu.hackathon.ingredient_manager.data.model;

public class RecipeIngredient {

    public String name;
    public String quantity;
    public String preparation;

    public RecipeIngredient(String name, String quantity, String preparation) {
        this.name = name;
        this.quantity = quantity;
        this.preparation = preparation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }
}
