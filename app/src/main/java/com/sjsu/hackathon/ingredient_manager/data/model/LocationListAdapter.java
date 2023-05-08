package com.sjsu.hackathon.ingredient_manager.data.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.hackathon.ingredient_manager.R;
import com.sjsu.hackathon.ingredient_manager.data.handler.LocationHandler;

import java.util.List;

public class LocationListAdapter extends RecyclerView.Adapter<LocationItemHolder> {
    private List<LocationItem> list;
    private LocationHandler handler;

    public LocationListAdapter(List<LocationItem> list, LocationHandler handler) {
        this.list = list;
        this.handler = handler;
    }

    @NonNull
    @Override
    public LocationItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_item, parent, false);

        return new LocationItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationItemHolder holder, int position) {
        LocationItem data = list.get(position);
        holder.setLocationNameText(data.getLocation().getName());
        holder.setLocation(data.getLocation());
        holder.getDeleteImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.remove(data.getLocation().getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
