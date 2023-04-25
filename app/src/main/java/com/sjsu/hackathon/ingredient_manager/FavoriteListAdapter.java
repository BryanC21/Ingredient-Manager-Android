package com.sjsu.hackathon.ingredient_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteItemHolder> {
    private List<FavoriteItem> list;

    public FavoriteListAdapter(List<FavoriteItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public FavoriteItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_item, parent, false);

        return new FavoriteItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteItemHolder holder, int position) {
        FavoriteItem data = list.get(position);
        System.out.println(data.getRecipe().getName());
        holder.setRecipeNameText(data.getRecipe().getName());
        holder.setRecipe(data.getRecipe());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
