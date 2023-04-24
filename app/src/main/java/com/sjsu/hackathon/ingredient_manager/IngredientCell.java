package com.sjsu.hackathon.ingredient_manager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.hackathon.ingredient_manager.data.model.Recipe;

public class IngredientCell extends RecyclerView.ViewHolder {

    public TextView titleTextView;
    public TextView descriptionTextView;

    private String id;
    private String name;
    private double quantity;
    private String locationId;
    private Date expirationTime;
    private Date createTime;
    private String img;
    private String notes;
    private String categoryId;
    private String unitId;

    public void setIngredientDetails(String id, String name, double quantity, String locationId, Date expirationTime,
                                     Date createTime, String img, String notes, String categoryId, String unitId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.locationId = locationId;
        this.expirationTime = expirationTime;
        this.createTime = createTime;
        this.img = img;
        this.notes = notes;
        this.categoryId = categoryId;
        this.unitId = unitId;
    }

    public IngredientCell(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.title_text_view);
        descriptionTextView = itemView.findViewById(R.id.description_text_view);

        // Set an OnClickListener on the root view of the cell layout
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for this cell
                // Navigate to a new fragment
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("name", name);
                bundle.putDouble("quantity", quantity);
                bundle.putString("locationId", locationId);
                bundle.putSerializable("expirationTime", expirationTime);
                bundle.putSerializable("createTime", createTime);
                bundle.putString("img", img);
                bundle.putString("notes", notes);
                bundle.putString("categoryId", categoryId);
                bundle.putString("unitId", unitId);
                bundle.putInt("viewID", v.getId());

                NavController navController = Navigation.findNavController(v);

                NavOptions navOptions = new NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setPopUpTo(navController.getCurrentDestination().getId(), false)
                        .build();

                navController.navigate(R.id.action_navigation_dashboard_to_fragment_ingredient_details, bundle, navOptions);

            }
        });
    }
}
