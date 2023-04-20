package com.sjsu.hackathon.ingredient_manager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjsu.hackathon.ingredient_manager.data.handler.CategoryHandler;
import com.sjsu.hackathon.ingredient_manager.data.handler.LocationHandler;
import com.sjsu.hackathon.ingredient_manager.data.handler.UnitHandler;
import com.sjsu.hackathon.ingredient_manager.data.listener.CategoryListener;
import com.sjsu.hackathon.ingredient_manager.data.listener.LocationListener;
import com.sjsu.hackathon.ingredient_manager.data.listener.UnitListener;
import com.sjsu.hackathon.ingredient_manager.data.model.Category;
import com.sjsu.hackathon.ingredient_manager.data.model.Location;
import com.sjsu.hackathon.ingredient_manager.data.model.Unit;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ingredient_details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ingredient_details extends Fragment {

    private String id;
    private View rootView;

    private String name;

    private double quantity;
    private String locationId;
    private Date expirationTime;
    private Date createTime;
    private String img;
    private String notes;
    private String categoryId;
    private String unitId;

    private NavController navController;

    public ingredient_details() {
        // Required empty public constructor
    }
    public static ingredient_details newInstance() {
        ingredient_details fragment = new ingredient_details();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            navController.popBackStack(); // Navigate back to previous fragment
            return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_ingredient_details, container, false);

        //---------
        if (getArguments() != null) {

            // unused
            id = getArguments().getString("id");

            // Name
            TextView name = rootView.findViewById(R.id.ingredient_details_name);
            name.setText("Name: " + getArguments().getString("name"));

            // Quantity
            TextView quantity = rootView.findViewById(R.id.ingredient_details_quantity);
            quantity.setText("Quantity: " + getArguments().getDouble("quantity"));

            // Expiration Time
            expirationTime = (Date) getArguments().getSerializable("expirationTime");
            TextView expirationTime = rootView.findViewById(R.id.ingredient_details_expiration);
            expirationTime.setText("Expiration: " + this.expirationTime.toString());

            // Create Time
            createTime = (Date) getArguments().getSerializable("createTime");
            TextView createTime = rootView.findViewById(R.id.ingredient_details_created);
            createTime.setText("Created: " + this.createTime.toString());

            // Image TODO: add image
            img = getArguments().getString("img");
            TextView imgView = rootView.findViewById(R.id.ingredient_details_img);
            imgView.setText("Image: " + img);

            // Notes
            notes = getArguments().getString("notes");
            TextView notesView = rootView.findViewById(R.id.ingredient_details_notes);
            notesView.setText("Notes: " + notes);

            // Unit
            unitId = getArguments().getString("unitId");
            UnitListener unitListener = new UnitListener() {
                @Override
                public void onUnitGetAllFinish(ArrayList<Unit> dataList) {}
                @Override
                public void onUnitGetFinish(Unit data) {
                    unitId = data.getName();
                    TextView unit = rootView.findViewById(R.id.ingredient_details_unit);
                    unit.setText("Unit: " + unitId);
                }
                @Override
                public void onDataSuccess(String reason) {}
                @Override
                public void onDataFail(String reason) {}
            };
            UnitHandler unitHandler = new UnitHandler(unitListener);
            unitHandler.get(unitId);

            // Location
            locationId = getArguments().getString("locationId");
            LocationListener locationListener = new LocationListener() {

                @Override
                public void onLocationGetAllFinish(ArrayList<Location> dataList) {}

                @Override
                public void onLocationGetFinish(Location data) {
                    locationId = data.getName();
                    TextView location = rootView.findViewById(R.id.ingredient_details_location);
                    location.setText("Location: " + locationId);
                }

                @Override
                public void onDataSuccess(String reason) {}

                @Override
                public void onDataFail(String reason) {}
            };
            LocationHandler locationHandler = new LocationHandler(locationListener);
            locationHandler.get(locationId);

            // Category
            categoryId = getArguments().getString("categoryId");
            CategoryListener categoryListener = new CategoryListener() {

                @Override
                public void onCategoryGetAllFinish(ArrayList<Category> dataList) {

                }

                @Override
                public void onCategoryGetFinish(Category data) {
                    categoryId = data.getName();
                    TextView category = rootView.findViewById(R.id.ingredient_details_category);
                    category.setText("Category: " + categoryId);
                }

                @Override
                public void onDataSuccess(String reason) {}
                @Override
                public void onDataFail(String reason) {}
            };
            CategoryHandler categoryHandler = new CategoryHandler(categoryListener);
            categoryHandler.get(categoryId);

        }
        //---------

        return rootView;
    }


}