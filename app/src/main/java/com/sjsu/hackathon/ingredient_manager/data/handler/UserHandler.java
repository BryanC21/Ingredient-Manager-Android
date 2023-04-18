package com.sjsu.hackathon.ingredient_manager.data.handler;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sjsu.hackathon.ingredient_manager.data.listener.UnitListener;
import com.sjsu.hackathon.ingredient_manager.data.listener.UserListener;

public class UserHandler {
    private FirebaseUser user;

    private final DatabaseReference dbRef;
    private final UserListener listener;

    public UserHandler(UserListener listener) {
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("dev")
                .child(this.user.getUid());
        this.listener = listener;
    }

    public String getId() {
        return this.user.getUid();
    }

    public void exists() {
        FirebaseDatabase.getInstance().getReference("dev").child(this.user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    listener.onExistsFinish(true);
                }else{
                    listener.onExistsFinish(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
