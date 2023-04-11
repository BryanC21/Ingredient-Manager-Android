package com.sjsu.hackathon.ingredient_manager.data.handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sjsu.hackathon.ingredient_manager.data.listener.IngredientListener;
import com.sjsu.hackathon.ingredient_manager.data.model.Ingredient;

import java.util.ArrayList;
import java.util.Date;

public class IngredientHandler {
    private final DatabaseReference dbRef;

    private final IngredientListener listener;

    public IngredientHandler(IngredientListener listener) {
        UserHandler userHandler = new UserHandler();
        dbRef = FirebaseDatabase.getInstance().getReference("dev")
                .child(userHandler.getId())
                .child("ingredients");
        this.listener = listener;
    }

    public void add(String name, double quantity,
                                 String img, String notes,
                                 Date expirationTime, Date createTime, String locationId,
                                 String categoryId, String unitId) {
        DatabaseReference list = dbRef.push();

        Ingredient data = new Ingredient(name, quantity, img, notes,
                expirationTime, createTime, locationId,
                categoryId, unitId);
        list.setValue(data).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        listener.onDataSuccess("Success");
                    } else {
                        listener.onDataFail("Fail");
                    }
                }
        );
    }

    public void getAll() {
        dbRef.get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        ArrayList<Ingredient> list = new ArrayList<>();
                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                            Ingredient data = snapshot.getValue(Ingredient.class);
                            data.setId(snapshot.getKey());
                            list.add(data);
                        }
                        listener.onGetAllFinish(list);
                    } else {
                        listener.onDataFail("No data");
                    }
                });
    }

    public void get(String id) {
        dbRef.child(id).get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        Ingredient data = task.getResult().getValue(Ingredient.class);
                        data.setId(task.getResult().getKey());
                        listener.onGetFinish(data);
                    } else {
                        listener.onDataFail("No data");
                    }
                });
    }

    public void edit(String id, String name,
                                   double quantity, String img, String notes,
                                   Date expirationTime, Date createTime, String locationId,
                                   String categoryId, String unitId) {
        Ingredient data = new Ingredient(name, quantity, img, notes,
                expirationTime, createTime, locationId,
                categoryId, unitId);
        dbRef.child(id).setValue(data).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        listener.onDataSuccess("Edit Success");
                    } else {
                        listener.onDataFail("Edit Fail");
                    }
                }
        );

    }

    public void remove(String id) {
        dbRef.child(id).removeValue().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        listener.onDataSuccess("Remove Success");
                    } else {
                        listener.onDataFail("Remove Fail");
                    }
                }
        );
    }
}
