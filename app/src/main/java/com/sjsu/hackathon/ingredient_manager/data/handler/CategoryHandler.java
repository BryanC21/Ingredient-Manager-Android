package com.sjsu.hackathon.ingredient_manager.data.handler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sjsu.hackathon.ingredient_manager.data.listener.CategoryListener;
import com.sjsu.hackathon.ingredient_manager.data.model.Category;

import java.util.ArrayList;

public class CategoryHandler {
    private final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("dev")
            .child("categories");

    private final CategoryListener listener;

    public CategoryHandler(CategoryListener listener) {
        this.listener = listener;
    }

    public void add(String name) {
        DatabaseReference list = dbRef.push();

        Category data = new Category(name);
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
                        ArrayList<Category> list = new ArrayList<>();
                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                            Category data = snapshot.getValue(Category.class);
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
                        Category data = task.getResult().getValue(Category.class);
                        data.setId(task.getResult().getKey());
                        listener.onGetFinish(data);
                    } else {
                        listener.onDataFail("No data");
                    }
                });
    }

    public void edit(String id, String name) {
        Category data = new Category(name);
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
