package com.sjsu.hackathon.ingredient_manager.data.model;

import androidx.annotation.NonNull;

public class LocationItem {
    private Location location;

    public LocationItem(@NonNull final Location location) {
        this.location = location;
    }

    @NonNull
    public Location getLocation() {
        return location;
    }

    public void setLocation(@NonNull final Location location) {
        this.location = location;
    }
}
