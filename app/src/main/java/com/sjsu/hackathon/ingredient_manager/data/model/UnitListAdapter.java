package com.sjsu.hackathon.ingredient_manager.data.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.hackathon.ingredient_manager.R;
import com.sjsu.hackathon.ingredient_manager.data.handler.UnitHandler;

import java.util.List;

public class UnitListAdapter extends RecyclerView.Adapter<UnitItemHolder> {
    private List<UnitItem> list;
    private UnitHandler handler;

    public UnitListAdapter(List<UnitItem> list, UnitHandler handler) {
        this.list = list;
        this.handler = handler;
    }

    @NonNull
    @Override
    public UnitItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.unit_item, parent, false);

        return new UnitItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitItemHolder holder, int position) {
        UnitItem data = list.get(position);
        System.out.println(data.getUnit().getName());
        holder.setUnitNameText(data.getUnit().getName());
        holder.setUnit(data.getUnit());
        holder.getDeleteImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.remove(data.getUnit().getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
