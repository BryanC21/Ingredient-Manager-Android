package com.sjsu.hackathon.ingredient_manager.data.model;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.hackathon.ingredient_manager.R;

public class RecipeInstructionHolder extends RecyclerView.ViewHolder {
    private TextView recipe_instruction;

    public RecipeInstructionHolder(final View itemView) {
        super(itemView);
        recipe_instruction = itemView.findViewById(R.id.recipe_instruction);
    }

    public void set(String instruction) {
        this.recipe_instruction.setText(instruction);
    }
}
