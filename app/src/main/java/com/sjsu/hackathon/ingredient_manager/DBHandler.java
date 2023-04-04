package com.sjsu.hackathon.ingredient_manager;


import android.content.ContentValues;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sjsu.hackathon.ingredient_manager.models.Category;
import com.sjsu.hackathon.ingredient_manager.models.Ingredient;
import com.sjsu.hackathon.ingredient_manager.models.Location;
import com.sjsu.hackathon.ingredient_manager.models.Unit;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.internal.cache.DiskLruCache;

public class DBHandler {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = database.getReference("dev");
    public void addNewIngredient(String name, double quantity, String img, String notes,
                                 Date expirationTime, Date createTime, String locationId,
                                 String categoryId, String unitId) {
        DatabaseReference ingredientList = dbRef.child("ingredients").push();

        Ingredient ingredient = new Ingredient(name, quantity, img, notes,
                expirationTime, createTime, locationId,
                categoryId, unitId);
        ingredientList.setValue(ingredient);
        System.out.println("Ingredient added");
    }

    public void addNewLocation(String name) {
        DatabaseReference locationList = dbRef.child("locations").push();

        Location location = new Location(name);
        locationList.setValue(location);
        System.out.println("Location added");
    }

    public void addNewCategory(String name) {
        DatabaseReference categoryList = dbRef.child("categories").push();

        Category category = new Category(name);
        categoryList.setValue(category);
        System.out.println("Category added");
    }

    public void addNewUnit(String name) {
        DatabaseReference unitList = dbRef.child("units").push();

        Unit unit = new Unit(name);
        unitList.setValue(unit);
        System.out.println("Unit added");
    }

    public void removeData(String tableName, String startYear, String endYear) {
    }

    public void getIngredients(DataListener listener) {
        ArrayList<Ingredient> list = new ArrayList<>();
        dbRef.child("ingredients").get().addOnCompleteListener(
            new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DataSnapshot ingredientSnapshot : task.getResult().getChildren()) {
                            Ingredient ingredient = ingredientSnapshot.getValue(Ingredient.class);
                            ingredient.setId(ingredientSnapshot.getKey());
                            list.add(ingredient);
                        }
                        listener.onIngredientDataFinish(list);
                    } else {
                        listener.onDataFail("No data");
                    }
                }
            });
    }

    public void getLocations(DataListener listener) {
        ArrayList<Location> list = new ArrayList<>();
        dbRef.child("locations").get().addOnCompleteListener(
                new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DataSnapshot locationSnapshot : task.getResult().getChildren()) {
                                Location location = locationSnapshot.getValue(Location.class);
                                location.setId(locationSnapshot.getKey());
                                list.add(location);
                            }
                            listener.onLocationDataFinish(list);
                        } else {
                            listener.onDataFail("No data");
                        }
                    }
                });
    }

    public void getCategories(DataListener listener) {
        ArrayList<Category> list = new ArrayList<>();
        dbRef.child("categories").get().addOnCompleteListener(
                new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DataSnapshot categorySnapshot : task.getResult().getChildren()) {
                                Category category = categorySnapshot.getValue(Category.class);
                                category.setId(categorySnapshot.getKey());
                                list.add(category);
                            }
                            listener.onCategoryDataFinish(list);
                        } else {
                            listener.onDataFail("No data");
                        }
                    }
                });
    }

    public void getUnits(DataListener listener) {
        ArrayList<Unit> list = new ArrayList<>();
        dbRef.child("units").get().addOnCompleteListener(
                new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DataSnapshot unitSnapshot : task.getResult().getChildren()) {
                                Unit unit = unitSnapshot.getValue(Unit.class);
                                unit.setId(unitSnapshot.getKey());
                                list.add(unit);
                            }
                            listener.onUnitDataFinish(list);
                        } else {
                            listener.onDataFail("No data");
                        }
                    }
                });
    }
}
