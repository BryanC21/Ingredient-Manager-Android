package com.sjsu.hackathon.ingredient_manager;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IngredientCell extends RecyclerView.ViewHolder {

    public TextView titleTextView;
    public TextView descriptionTextView;

    public IngredientCell(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.title_text_view);
        descriptionTextView = itemView.findViewById(R.id.description_text_view);
    }
}
