package com.sjsu.hackathon.ingredient_manager.data.handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sjsu.hackathon.ingredient_manager.data.listener.CategoryListener;
import com.sjsu.hackathon.ingredient_manager.data.listener.UserListener;
import com.sjsu.hackathon.ingredient_manager.data.model.Category;

import java.util.ArrayList;

public class CategoryHandler implements UserListener {
    private final DatabaseReference dbRef;

    private final CategoryListener listener;

    public CategoryHandler(CategoryListener listener) {
        UserHandler userHandler = new UserHandler(this);
        dbRef = FirebaseDatabase.getInstance().getReference("dev")
                .child(userHandler.getId())
                .child("categories");
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
                        listener.onCategoryGetAllFinish(list);
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
                        if (data != null) {
                            data.setId(task.getResult().getKey());
                            listener.onCategoryGetFinish(data);
                        } else {
                            listener.onDataFail("Corrupted data");
                        }
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

    @Override
    public void onDataSuccess(String reason) {

    }

    @Override
    public void onDataFail(String reason) {

    }

    @Override
    public void onExistsFinish(boolean exists) {

    }
}
