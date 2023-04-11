package com.sjsu.hackathon.ingredient_manager.data.handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserHandler {
    private FirebaseUser user;

    public UserHandler() {
        this.user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public String getId() {
        return this.user.getUid();
    }
}
