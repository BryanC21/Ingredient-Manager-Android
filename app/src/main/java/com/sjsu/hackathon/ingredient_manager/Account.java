package com.sjsu.hackathon.ingredient_manager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.sjsu.hackathon.ingredient_manager.data.handler.UnitHandler;
import com.sjsu.hackathon.ingredient_manager.data.listener.UnitListener;
import com.sjsu.hackathon.ingredient_manager.data.model.Recipe;
import com.sjsu.hackathon.ingredient_manager.data.model.Unit;
import com.sjsu.hackathon.ingredient_manager.data.model.UnitItem;
import com.sjsu.hackathon.ingredient_manager.data.model.UnitListAdapter;
import com.sjsu.hackathon.ingredient_manager.databinding.FragmentAccountBinding;
import com.sjsu.hackathon.ingredient_manager.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class Account extends Fragment implements UnitListener {
    private View rootView;
    private UnitHandler handler;
    private RecyclerView recyclerView;
    private FragmentAccountBinding binding;

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
        rootView =  inflater.inflate(R.layout.fragment_account, container, false);
        handler = new UnitHandler(this);
        handler.getAll();
        binding = FragmentAccountBinding.inflate(inflater, container, false);

        try {
            recyclerView = rootView.findViewById(R.id.user_unit_list);
            RecyclerView.ItemDecoration itemDecoration = new
                    DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(itemDecoration);
        } catch (Exception e) {
            System.out.println(e);
        }

        ImageButton button = rootView.findViewById(R.id.submitAddNewItem);
        EditText unitText = rootView.findViewById(R.id.addNewUnit);
        button.setOnClickListener(v -> {
            System.out.println("here");
            String unitStr = unitText.getText().toString();
            System.out.println(unitStr);
            if (!unitStr.isEmpty()) {
                handler.add(unitStr);
                unitText.setText("");
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onDataSuccess(String reason) {
        if (reason.equals("Remove Success")) {
            Toast.makeText(this.getContext(), "Unit Removed Successfully", Toast.LENGTH_SHORT).show();
        } else if (reason.equals("Success")) {
            Toast.makeText(this.getContext(), "Unit Added Successfully", Toast.LENGTH_SHORT).show();
        }
        handler.getAll();
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

        UnitListAdapter adapter = new UnitListAdapter(h, handler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    @Override
    public void onUnitGetFinish(Unit data) {

    }
}