package com.sjsu.hackathon.ingredient_manager.data.model;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.hackathon.ingredient_manager.R;

public class UnitItemHolder extends RecyclerView.ViewHolder {
    private TextView unitNameText;
    private ImageButton deleteImageButton;
    private Unit unit;

    public UnitItemHolder(final View itemView) {
        super(itemView);
        unitNameText = itemView.findViewById(R.id.unit_name);
        deleteImageButton = itemView.findViewById(R.id.deleteUnitButton);
    }

    public void setUnitNameText(String text) {
        this.unitNameText.setText(text);
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setDeleteImageButton(ImageButton deleteImageButton) {
        this.deleteImageButton = deleteImageButton;
    }

    @NonNull
    public ImageButton getDeleteImageButton() {
        return deleteImageButton;
    }

}
