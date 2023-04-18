package com.sjsu.hackathon.ingredient_manager.data.handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sjsu.hackathon.ingredient_manager.data.listener.UnitListener;
import com.sjsu.hackathon.ingredient_manager.data.listener.UserListener;
import com.sjsu.hackathon.ingredient_manager.data.model.Unit;

import java.util.ArrayList;

public class UnitHandler implements UserListener {
    private final DatabaseReference dbRef;

    private final UnitListener listener;

    public UnitHandler(UnitListener listener) {
        UserHandler userHandler = new UserHandler(this);
        dbRef = FirebaseDatabase.getInstance().getReference("dev")
                .child(userHandler.getId())
                .child("units");
        this.listener = listener;
    }

    public void add(String name) {
        DatabaseReference list = dbRef.push();

        Unit data = new Unit(name);
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
                        ArrayList<Unit> list = new ArrayList<>();
                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                            Unit data = snapshot.getValue(Unit.class);
                            data.setId(snapshot.getKey());
                            list.add(data);
                        }
                        listener.onUnitGetAllFinish(list);
                    } else {
                        listener.onDataFail("No data");
                    }
                });
    }

    public void get(String id) {
        dbRef.child(id).get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        Unit data = task.getResult().getValue(Unit.class);
                        data.setId(task.getResult().getKey());
                        listener.onUnitGetFinish(data);
                    } else {
                        listener.onDataFail("No data");
                    }
                });
    }

    public void edit(String id, String name) {
        Unit data = new Unit(name);
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
