package com.sjsu.hackathon.ingredient_manager;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.sjsu.hackathon.ingredient_manager.data.model.Ingredient;

import java.util.ArrayList;
import java.util.Date;

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
                .inflate(R.layout.favorite_item, parent, false);
        return new IngredientCell(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientCell holder, int position) {
        DataSnapshot data = listData.get(position);
        holder.setIngredientDetails(data.child("id").getValue(String.class), data.child("name").getValue(String.class), data.child("quantity").getValue(Double.class),
                data.child("locationId").getValue(String.class), data.child("expirationTime").getValue(Date.class), data.child("createTime").getValue(Date.class),
                data.child("img").getValue(String.class), data.child("notes").getValue(String.class), data.child("categoryId").getValue(String.class), data.child("unitId").getValue(String.class));
        holder.titleTextView.setText(data.child("name").getValue(String.class));
        holder.descriptionTextView.setText(data.child("notes").getValue(String.class));
    }

    @Override
    public int getItemCount() {
        return (int) listData.size();
    }
}
