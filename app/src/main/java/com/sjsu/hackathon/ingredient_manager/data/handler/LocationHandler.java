package com.sjsu.hackathon.ingredient_manager.data.handler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sjsu.hackathon.ingredient_manager.data.listener.LocationListener;
import com.sjsu.hackathon.ingredient_manager.data.listener.UserListener;
import com.sjsu.hackathon.ingredient_manager.data.model.Location;

import java.util.ArrayList;

public class LocationHandler implements UserListener {
    private final DatabaseReference dbRef;

    private final LocationListener listener;

    public LocationHandler(LocationListener listener) {
        UserHandler userHandler = new UserHandler(this);
        dbRef = FirebaseDatabase.getInstance().getReference("dev")
                .child(userHandler.getId())
                .child("locations");
        this.listener = listener;
    }

    public void add(String name) {
        DatabaseReference list = dbRef.push();

        Location data = new Location(name);
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
                        ArrayList<Location> list = new ArrayList<>();
                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                            Location data = snapshot.getValue(Location.class);
                            data.setId(snapshot.getKey());
                            list.add(data);
                        }
                        listener.onLocationGetAllFinish(list);
                    } else {
                        listener.onDataFail("No data");
                    }
                });
    }

    public void get(String id) {
        dbRef.child(id).get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        Location data = task.getResult().getValue(Location.class);
                        if (data != null) {
                            data.setId(task.getResult().getKey());
                            listener.onLocationGetFinish(data);
                        } else {
                            listener.onDataFail("Corrupted data");
                        }
                    } else {
                        listener.onDataFail("No data");
                    }
                });
    }

    public void edit(String id, String name) {
        Location data = new Location(name);
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
