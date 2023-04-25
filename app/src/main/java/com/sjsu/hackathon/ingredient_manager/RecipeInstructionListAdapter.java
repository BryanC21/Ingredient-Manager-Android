package com.sjsu.hackathon.ingredient_manager;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sjsu.hackathon.ingredient_manager.data.model.RecipeIngredient;

import java.util.ArrayList;

public class RecipeInstructionListAdapter extends ArrayAdapter<String>  {

    RecipeIngredient dataStore;
    ArrayList<RecipeIngredient> list;

    public RecipeInstructionListAdapter(Context context, ArrayList<String> list){
        super(context, 0, list);
        list = new ArrayList<>();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String instruction = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_instruction_cell,
                    parent, false);
        }
        TextView nameView = convertView.findViewById(R.id.recipe_instruction);
        nameView.setText(instruction);
        return convertView;
    }
}
