package com.sjsu.hackathon.ingredient_manager.ui.home;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.sjsu.hackathon.ingredient_manager.MainActivity;
import com.sjsu.hackathon.ingredient_manager.data.handler.IngredientHandler;
import com.sjsu.hackathon.ingredient_manager.data.handler.LocationHandler;
import com.sjsu.hackathon.ingredient_manager.data.handler.UnitHandler;
import com.sjsu.hackathon.ingredient_manager.data.listener.IngredientListener;
import com.sjsu.hackathon.ingredient_manager.data.listener.LocationListener;
import com.sjsu.hackathon.ingredient_manager.data.listener.UnitListener;
import com.sjsu.hackathon.ingredient_manager.data.model.Ingredient;
import com.sjsu.hackathon.ingredient_manager.data.model.Location;
import com.sjsu.hackathon.ingredient_manager.data.model.Unit;
import com.sjsu.hackathon.ingredient_manager.databinding.FragmentHomeBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment implements IngredientListener, UnitListener, LocationListener {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle b = getActivity().getIntent().getExtras();
        String text = b.getString("label");

        UnitHandler unit = new UnitHandler(this);
        unit.getAll();

        LocationHandler loc = new LocationHandler(this);
        loc.getAll();

        binding.unit.setPrompt("Select a unit");
        binding.location.setPrompt("Select a location");

        EditText dt = binding.dateText;
        dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the current date from the system
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Create a DatePickerDialog and set the listener for date selection
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), // Your activity context
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                // Do something with the selected date
                                // e.g. update the EditText with the selected date
                                String selectedDate = (month + 1) + "/" + day + "/" + year;
                                binding.dateText.setText(selectedDate);
                            }
                        },
                        year, month, day);

                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });

        EditText e = binding.itemName;
        if (text != null && text.length() != 0) {
            e.setText(text);
        }

        EditText n = binding.notesText;
        EditText q = binding.quantityText;
        EditText d = binding.dateText;
        Button submit = binding.submitItem;
        submit.setOnClickListener(view -> {
            Location l = (Location) binding.location.getSelectedItem();
            Unit u = (Unit) binding.unit.getSelectedItem();

            if (e.getText().toString().isEmpty() || q.getText().toString().isEmpty() || d.getText().toString().isEmpty() ||
                    l == null || l.getId().isEmpty() || u == null || u.getId().isEmpty()) {
                Toast.makeText(this.getContext(), "Mandatory fields missing", Toast.LENGTH_SHORT).show();
                return;
            }
            IngredientHandler dbHandler = new IngredientHandler(this);
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                date = dateFormat.parse(d.getText().toString());
            } catch (ParseException ex) {
                // leave it be to use current date
            }

            dbHandler.add(e.getText().toString(), Float.parseFloat(q.getText().toString()), "img1", n.getText().toString(), date, date, "-NS8eopaXJh9mv5HI-Hs",
                    "-NS8eopYaRGPiJGW9w32", "-NS8eopO_GxzgPnsPGBc", l.getId());
            dbHandler.getAll();
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton myButton = binding.capture;
        myButton.setOnClickListener(v -> ((MainActivity) getActivity()).openCamera());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onIngredientGetAllFinish(ArrayList<Ingredient> dataList) {
        System.out.println(dataList);
    }

    @Override
    public void onIngredientGetFinish(Ingredient data) {
        System.out.println(data);
    }

    @Override
    public void onDataSuccess(String reason) {
        Toast.makeText(this.getContext(), "Successfully submitted ingredient", Toast.LENGTH_SHORT).show();
        binding.notesText.setText("");
        binding.quantityText.setText("");
        binding.dateText.setText("");
        binding.itemName.setText("");
        binding.location.setSelection(0);
        binding.unit.setSelection(0);
    }

    @Override
    public void onDataFail(String reason) {
        Toast.makeText(this.getContext(), "Failed to submit ingredient", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onLocationGetAllFinish(ArrayList<Location> dataList) {
        System.out.println(dataList);
        ArrayAdapter<Location> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, dataList);
        this.binding.location.setAdapter(adapter);
    }

    @Override
    public void onLocationGetFinish(Location data) {
        System.out.println(data);
    }

    @Override
    public void onUnitGetAllFinish(ArrayList<Unit> dataList) {
        System.out.println(dataList);
        ArrayAdapter<Unit> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, dataList);
        this.binding.unit.setAdapter(adapter);
    }

    @Override
    public void onUnitGetFinish(Unit data) {
        System.out.println(data);
    }
}