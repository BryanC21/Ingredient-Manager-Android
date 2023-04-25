package com.sjsu.hackathon.ingredient_manager.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class RecipeIngredient implements Parcelable {

    public String name;
    public String quantity;
    public String preparation;

    public RecipeIngredient(String name, String quantity, String preparation) {
        this.name = name;
        this.quantity = quantity;
        this.preparation = preparation;
    }

    protected RecipeIngredient(Parcel in) {
        name = in.readString();
        quantity = in.readString();
        preparation = in.readString();
    }

    public static final Creator<RecipeIngredient> CREATOR = new Creator<RecipeIngredient>() {
        @Override
        public RecipeIngredient createFromParcel(Parcel in) {
            return new RecipeIngredient(in);
        }

        @Override
        public RecipeIngredient[] newArray(int size) {
            return new RecipeIngredient[size];
        }
    };

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

    public JSONObject toJson() {
        JSONObject ingredientJson = new JSONObject();
        try {
            ingredientJson.put("name", this.name);
            ingredientJson.put("quantity", this.quantity);
            ingredientJson.put("preparation", this.preparation);
        } catch (Exception e) {
            Log.e("Json fail", e.getMessage());
        }
        return ingredientJson;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(quantity);
        parcel.writeString(preparation);
    }
}
