package com.sjsu.hackathon.ingredient_manager;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.sjsu.hackathon.ingredient_manager.data.model.Ingredient;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientCell>  {

    DataSnapshot dataStore;
    ArrayList<DataSnapshot> listData;

    public IngredientListAdapter (){
        listData = new ArrayList<>();
    }

    public IngredientListAdapter(DataSnapshot ds){
        dataStore = ds;
        //make it into an arraylist because this view wants to use indexes for each row
        listData = new ArrayList<>();
        for (DataSnapshot dsx : ds.getChildren()) {
            listData.add(dsx);
        }
    }

    @Override
    public IngredientCell onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_cell, parent, false);
        return new IngredientCell(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientCell holder, int position) {
        DataSnapshot data = listData.get(position);
        holder.titleTextView.setText(data.child("ingredient").getValue(String.class));
        holder.descriptionTextView.setText(data.child("notes").getValue(String.class));
    }

    @Override
    public int getItemCount() {
        return (int) listData.size();
    }
}
