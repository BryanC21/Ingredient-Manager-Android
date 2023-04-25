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

public class CheckListAdapter extends RecyclerView.Adapter<CheckListCell>{

    ArrayList<DataSnapshot> listData;
    private ArrayList<String> checkedItems = new ArrayList<>();

    public CheckListAdapter (){
        listData = new ArrayList<>();
    }

    public CheckListAdapter(DataSnapshot ds){
        //make it into an arraylist because this view wants to use indexes for each row
        listData = new ArrayList<>();
        for (DataSnapshot dsx : ds.getChildren()) {
            listData.add(dsx);
        }
    }

    @NonNull
    @Override
    public CheckListCell onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checklist_cell, parent, false);
        return new CheckListCell(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckListCell holder, int position) {
        DataSnapshot data = listData.get(position);
        holder.titleTextView.setText(data.child("name").getValue(String.class));

        // If the box is checked add to the list of checked items as a string of the ingredient name
        holder.titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = holder.titleTextView.isChecked();
                if (isChecked) {
                    // Add the item to the list of checked items
                    checkedItems.add(data.child("name").getValue(String.class));
                } else {
                    // Remove the item from the list of checked items
                    checkedItems.remove(data.child("name").getValue(String.class));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (int) listData.size();
    }

    public ArrayList<String> getCheckedItems() {
        return checkedItems;
    }
}
