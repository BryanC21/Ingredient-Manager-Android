package com.sjsu.hackathon.ingredient_manager;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CheckListCell extends RecyclerView.ViewHolder {

    public CheckBox titleTextView;
    public CheckListCell(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.title_text_view2);
    }
}
