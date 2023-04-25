package com.sjsu.hackathon.ingredient_manager.ui.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.sjsu.hackathon.ingredient_manager.FirebaseUIActivity;
import com.sjsu.hackathon.ingredient_manager.MainActivity;
import com.sjsu.hackathon.ingredient_manager.data.handler.CategoryHandler;
import com.sjsu.hackathon.ingredient_manager.data.handler.IngredientHandler;
import com.sjsu.hackathon.ingredient_manager.data.handler.LocationHandler;
import com.sjsu.hackathon.ingredient_manager.data.handler.UnitHandler;
import com.sjsu.hackathon.ingredient_manager.data.listener.CategoryListener;
import com.sjsu.hackathon.ingredient_manager.data.listener.IngredientListener;
import com.sjsu.hackathon.ingredient_manager.data.listener.LocationListener;
import com.sjsu.hackathon.ingredient_manager.data.listener.UnitListener;
import com.sjsu.hackathon.ingredient_manager.data.model.Category;
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

public class HomeFragment extends Fragment implements IngredientListener, UnitListener, LocationListener, CategoryListener {

    private FragmentHomeBinding binding;
    private NavController navController;
    private OnBackPressedCallback onBackPressedCallback;

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

        CategoryHandler cat = new CategoryHandler(this);
        cat.getAll();

        binding.unit.setPrompt("Select a unit");
        binding.location.setPrompt("Select a location");
        binding.location.setPrompt("Select a category");

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
            Category c = (Category) binding.category.getSelectedItem();

            if (e.getText().toString().isEmpty() || q.getText().toString().isEmpty() || d.getText().toString().isEmpty() ||
                    l == null || l.getId().isEmpty() || l.getId().equals("-1") ||
                    u == null || u.getId().isEmpty() || u.getId().equals("-1") ||
                    c == null || c.getId().isEmpty() || c.getId().equals("-1")) {
                Toast.makeText(this.getContext(), "Mandatory fields missing", Toast.LENGTH_SHORT).show();
                return;
            }
            IngredientHandler dbHandler = new IngredientHandler(this);
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy", Locale.getDefault());
            try {
                date = dateFormat.parse(d.getText().toString());
            } catch (ParseException ex) {
                // leave it be to use current date
                System.out.println(ex);
            }

            dbHandler.add(e.getText().toString(), Float.parseFloat(q.getText().toString()), "img1", n.getText().toString(), date, new Date(), l.getId(),
                    c.getId(), u.getId(), "");
            dbHandler.getAll();
        });

        Button logout = binding.logoutButton;
        logout.setOnClickListener(view -> {
            AuthUI.getInstance()
                    .signOut(getContext())
                    .addOnCompleteListener(new OnCompleteListener<Void>(){

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(getActivity(), FirebaseUIActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            getActivity().finish();

                        }
                    });
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up open camera listener
        ImageButton myButton = binding.capture;
        myButton.setOnClickListener(v -> ((MainActivity) getActivity()).openCamera());
        // -----------------------------

        // Disable back button from leaving home fragment and returning to an empty activity
        navController = Navigation.findNavController(view);
        onBackPressedCallback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                NavDestination currentDestination = navController.getCurrentDestination();
                boolean isActivity = currentDestination instanceof ActivityNavigator.Destination;
                boolean isChildFragment = currentDestination.getParent() != null;
                if (!isActivity || !isChildFragment) {
                    navController.navigateUp();
                } else {
                    requireActivity().onBackPressed();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), onBackPressedCallback);
        // -----------------------------

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
        binding.category.setSelection(0);
    }

    @Override
    public void onDataFail(String reason) {
        Toast.makeText(this.getContext(), "Failed to submit ingredient", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onLocationGetAllFinish(ArrayList<Location> dataList) {
        System.out.println(dataList);
        List<Location> items = new ArrayList<>();
        items.add(new Location("Select a location", "-1"));
        items.addAll(dataList);
        ArrayAdapter<Location> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, items);
        this.binding.location.setAdapter(adapter);
    }

    @Override
    public void onLocationGetFinish(Location data) {
        System.out.println(data);
    }

    @Override
    public void onUnitGetAllFinish(ArrayList<Unit> dataList) {
        System.out.println(dataList);
        List<Unit> items = new ArrayList<>();
        items.add(new Unit("Select a unit", "-1"));
        items.addAll(dataList);
        ArrayAdapter<Unit> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, items);
        this.binding.unit.setAdapter(adapter);
    }

    @Override
    public void onUnitGetFinish(Unit data) {
        System.out.println(data);
    }

    @Override
    public void onCategoryGetAllFinish(ArrayList<Category> dataList) {
        System.out.println(dataList);
        List<Category> items = new ArrayList<>();
        items.add(new Category("Select a category", "-1"));
        items.addAll(dataList);
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, items);
        this.binding.category.setAdapter(adapter);
    }

    @Override
    public void onCategoryGetFinish(Category data) {

    }
}