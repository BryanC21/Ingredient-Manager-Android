package com.sjsu.hackathon.ingredient_manager.data.model;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.hackathon.ingredient_manager.R;

import java.util.ArrayList;

public class RecipeInstructionListAdapter extends RecyclerView.Adapter<RecipeInstructionHolder>  {

    RecipeIngredient dataStore;
    ArrayList<String> list;

    public RecipeInstructionListAdapter(ArrayList<String> list){
        this.list = list;
    }

    @NonNull
    @Override
    public RecipeInstructionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_instruction_cell, parent, false);

        return new RecipeInstructionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeInstructionHolder holder, int position) {
        String recipeInstruction = list.get(position);
        holder.set(recipeInstruction);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
