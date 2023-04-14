package com.sjsu.hackathon.ingredient_manager.controller;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sjsu.hackathon.ingredient_manager.data.listener.ChatgptListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Chatgpt {
    private static final String BASE_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "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";
    private static final String MODEL_ID = "gpt-3.5-turbo";

    private final RequestQueue requestQueue;

    public Chatgpt(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void sendMessage(String message, final ChatgptListener callback) {
        String url = BASE_URL;

        JSONObject requestBody = new JSONObject();
        JSONArray messages = new JSONArray();
        JSONObject messageJSON = new JSONObject();
        try {
            requestBody.put("model", MODEL_ID);
            messageJSON.put("role", "user");
            messageJSON.put("content", message);
            messages.put(messageJSON);
            requestBody.put("messages", messages);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(requestBody);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                requestBody,
                response -> callback.onSuccess(response.toString()),
                error -> {
                    System.out.println(error);
                    Log.e("OpenAIChat", "Error occurred while making API request: " + error.getMessage());
                    callback.onError();
                }
        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + API_KEY);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000, // Timeout in milliseconds
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }
}
