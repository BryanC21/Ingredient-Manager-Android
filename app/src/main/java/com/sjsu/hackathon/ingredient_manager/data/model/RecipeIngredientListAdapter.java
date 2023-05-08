package com.sjsu.hackathon.ingredient_manager.data.model;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.sjsu.hackathon.ingredient_manager.R;
import com.sjsu.hackathon.ingredient_manager.data.model.RecipeIngredient;
import com.sjsu.hackathon.ingredient_manager.data.model.RecipeIngredientHolder;
import com.sjsu.hackathon.ingredient_manager.data.model.UnitItemHolder;

import java.util.ArrayList;
import java.util.Date;

public class RecipeIngredientListAdapter extends RecyclerView.Adapter<RecipeIngredientHolder>  {

    RecipeIngredient dataStore;
    ArrayList<RecipeIngredient> list;

    public RecipeIngredientListAdapter(ArrayList<RecipeIngredient> list){
        this.list = list;
    }

    @NonNull
    @Override
    public RecipeIngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_ingredient_cell, parent, false);

        return new RecipeIngredientHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientHolder holder, int position) {
        RecipeIngredient recipeIngredient = list.get(position);
        holder.set(recipeIngredient.getName(), recipeIngredient.getQuantity(), recipeIngredient.getPreparation());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
