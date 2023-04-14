package com.sjsu.hackathon.ingredient_manager.data.listener;

public interface ChatgptListener {
    void onSuccess(String response);
    void onError();
}
