package com.sjsu.hackathon.ingredient_manager.data.model;

import androidx.annotation.NonNull;

public class UnitItem {
    private Unit unit;

    public UnitItem(@NonNull final Unit unit) {
        this.unit = unit;
    }

    @NonNull
    public Unit getUnit() {
        return unit;
    }

    public void setUnit(@NonNull final Unit unit) {
        this.unit = unit;
    }
}
