package com.sjsu.hackathon.ingredient_manager;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.sjsu.hackathon.ingredient_manager.data.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.Date;

public class RecipeIngredientListAdapter extends ArrayAdapter<RecipeIngredient>  {

    RecipeIngredient dataStore;
    ArrayList<RecipeIngredient> list;

    public RecipeIngredientListAdapter(Context context, ArrayList<RecipeIngredient> list){
        super(context, 0, list);
        list = new ArrayList<>();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecipeIngredient recipeIngredient = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_ingredient_cell, parent, false);
        }
        TextView nameView = convertView.findViewById(R.id.recipe_ingredient_name);
        nameView.setText(recipeIngredient.getName());
        TextView quantityView = convertView.findViewById(R.id.recipe_ingredient_quantity);
        quantityView.setText(recipeIngredient.getQuantity());
        TextView preparationView = convertView.findViewById(R.id.recipe_ingredient_preparation);
        preparationView.setText(recipeIngredient.getPreparation());
        return convertView;
    }
}
