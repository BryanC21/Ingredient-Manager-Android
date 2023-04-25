package com.sjsu.hackathon.ingredient_manager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sjsu.hackathon.ingredient_manager.data.model.Recipe;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeCell> {

    private ArrayList<Recipe> recipeList;

    public RecipeListAdapter() {
        recipeList = new ArrayList<>();
    }

    public RecipeListAdapter(ArrayList<Recipe> recipeList) {
        Log.d("RecipeListAdapter", "RecipeListAdapter: " + recipeList.size());
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeCell onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_cell, parent, false);
        return new RecipeCell(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeCell holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.titleTextView.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }
}
