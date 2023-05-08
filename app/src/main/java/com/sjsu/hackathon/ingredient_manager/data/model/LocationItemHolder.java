package com.sjsu.hackathon.ingredient_manager.data.model;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.hackathon.ingredient_manager.R;

public class LocationItemHolder extends RecyclerView.ViewHolder {
    private TextView locationNameText;
    private ImageButton deleteImageButton;
    private Location location;

    public LocationItemHolder(final View itemView) {
        super(itemView);
        locationNameText = itemView.findViewById(R.id.location_name);
        deleteImageButton = itemView.findViewById(R.id.deleteLocationButton);
    }

    public void setLocationNameText(String text) {
        this.locationNameText.setText(text);
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setDeleteImageButton(ImageButton deleteImageButton) {
        this.deleteImageButton = deleteImageButton;
    }

    @NonNull
    public ImageButton getDeleteImageButton() {
        return deleteImageButton;
    }

}
