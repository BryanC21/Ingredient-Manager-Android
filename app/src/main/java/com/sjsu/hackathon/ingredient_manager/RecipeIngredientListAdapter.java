package com.sjsu.hackathon.ingredient_manager;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.sjsu.hackathon.ingredient_manager.data.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.Date;

public class RecipeIngredientListAdapter extends RecyclerView.Adapter<OtherRecipeCell>  {

    RecipeIngredient dataStore;
    ArrayList<RecipeIngredient> list;

    public RecipeIngredientListAdapter(){
        list = new ArrayList<>();
    }

    public RecipeIngredientListAdapter(ArrayList<RecipeIngredient> list){
        this.list = list;
    }

    @Override
    public OtherRecipeCell onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_ingredient_cell, parent, false);
        return new OtherRecipeCell(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherRecipeCell holder, int position) {
        RecipeIngredient data = list.get(position);
        holder.setDetails(data);
        holder.nameTextView.setText(data.getName());
        holder.quantityTextView.setText(data.getQuantity());
        holder.preparationTextView.setText(data.getPreparation());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
