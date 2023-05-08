package com.sjsu.hackathon.ingredient_manager;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sjsu.hackathon.ingredient_manager.data.handler.LocationHandler;
import com.sjsu.hackathon.ingredient_manager.data.handler.UnitHandler;
import com.sjsu.hackathon.ingredient_manager.data.listener.LocationListener;
import com.sjsu.hackathon.ingredient_manager.data.listener.UnitListener;
import com.sjsu.hackathon.ingredient_manager.data.model.Location;
import com.sjsu.hackathon.ingredient_manager.data.model.LocationItem;
import com.sjsu.hackathon.ingredient_manager.data.model.LocationListAdapter;
import com.sjsu.hackathon.ingredient_manager.data.model.Unit;
import com.sjsu.hackathon.ingredient_manager.data.model.UnitItem;
import com.sjsu.hackathon.ingredient_manager.data.model.UnitListAdapter;
import com.sjsu.hackathon.ingredient_manager.databinding.FragmentAccountBinding;

import java.util.ArrayList;
import java.util.List;

public class Account extends Fragment implements UnitListener, LocationListener {
    private View rootView;
    private UnitHandler unitHandler;
    private LocationHandler locationHandler;
    private RecyclerView unitRecyclerView;
    private RecyclerView locationRecyclerView;
    private FragmentAccountBinding binding;

    private TextView username;

    private Button logoutButton;

    public Account() {
        // Required empty public constructor
    }

    public static Account newInstance(String param1, String param2) {
        Account fragment = new Account();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_account, container, false);
        unitHandler = new UnitHandler(this);
        unitHandler.getAll();

        // Change User Name
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userName = user.getDisplayName();
        username = rootView.findViewById(R.id.user_name_text_view);
        username.setText(userName);

        // Log out button
        logoutButton = rootView.findViewById(R.id.materialButton);
        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), FirebaseUIActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Toast.makeText(this.getContext(), "Logged Out", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            startActivity(intent);
        });

        locationHandler = new LocationHandler(this);
        locationHandler.getAll();

        binding = FragmentAccountBinding.inflate(inflater, container, false);

        try {
            unitRecyclerView = rootView.findViewById(R.id.user_unit_list);
            RecyclerView.ItemDecoration itemDecoration = new
                    DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
            unitRecyclerView.addItemDecoration(itemDecoration);

            locationRecyclerView = rootView.findViewById(R.id.user_locations_list);
            locationRecyclerView.addItemDecoration(itemDecoration);
        } catch (Exception e) {
            System.out.println(e);
        }

        ImageButton unitButton = rootView.findViewById(R.id.submitAddNewItem);
        EditText unitText = rootView.findViewById(R.id.addNewUnit);
        unitButton.setOnClickListener(v -> {
            System.out.println("here");
            String unitStr = unitText.getText().toString();
            System.out.println(unitStr);
            if (!unitStr.isEmpty()) {
                unitHandler.add(unitStr);
                unitText.setText("");
            }
        });

        ImageButton locationButton = rootView.findViewById(R.id.submitAddNewLocation);
        EditText locationText = rootView.findViewById(R.id.addNewLocation);
        locationButton.setOnClickListener(v -> {
            System.out.println("here");
            String locationStr = locationText.getText().toString();
            System.out.println(locationStr);
            if (!locationStr.isEmpty()) {
                locationHandler.add(locationStr);
                locationText.setText("");
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onDataSuccess(String reason) {
        if (reason.equals("Remove Success")) {
            Toast.makeText(this.getContext(), "Unit Removed Successfully", Toast.LENGTH_SHORT).show();
            unitHandler.getAll();
        } else if (reason.equals("Success")) {
            Toast.makeText(this.getContext(), "Unit Added Successfully", Toast.LENGTH_SHORT).show();
            unitHandler.getAll();
        } else if (reason.equals("Location Remove Success")) {
            Toast.makeText(this.getContext(), "Location Removed Successfully", Toast.LENGTH_SHORT).show();
            locationHandler.getAll();
        } else if (reason.equals("Location Success")) {
            Toast.makeText(this.getContext(), "Location Added Successfully", Toast.LENGTH_SHORT).show();
            locationHandler.getAll();
        }
    }

    @Override
    public void onDataFail(String reason) {

    }

    @Override
    public void onUnitGetAllFinish(ArrayList<Unit> dataList) {
        List<UnitItem> h = new ArrayList<>();
        dataList.stream().iterator().forEachRemaining(unit -> {
            h.add(new UnitItem(unit));
        });

        UnitListAdapter adapter = new UnitListAdapter(h, unitHandler);
        unitRecyclerView.setAdapter(adapter);
        unitRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    @Override
    public void onUnitGetFinish(Unit data) {

    }

    @Override
    public void onLocationGetAllFinish(ArrayList<Location> dataList) {
        List<LocationItem> h = new ArrayList<>();
        dataList.stream().iterator().forEachRemaining(location -> {
            h.add(new LocationItem(location));
        });

        LocationListAdapter adapter = new LocationListAdapter(h, locationHandler);
        locationRecyclerView.setAdapter(adapter);
        locationRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    @Override
    public void onLocationGetFinish(Location data) {

    }
}