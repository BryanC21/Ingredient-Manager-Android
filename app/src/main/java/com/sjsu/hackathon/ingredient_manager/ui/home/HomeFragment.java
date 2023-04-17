package com.sjsu.hackathon.ingredient_manager.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.sjsu.hackathon.ingredient_manager.data.handler.IngredientHandler;
import com.sjsu.hackathon.ingredient_manager.data.listener.IngredientListener;
import com.sjsu.hackathon.ingredient_manager.data.model.Ingredient;
import com.sjsu.hackathon.ingredient_manager.databinding.FragmentHomeBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment implements IngredientListener {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle b = getActivity().getIntent().getExtras();
        String text = b.getString("label");

        EditText e = binding.itemName;
        if (text != null && text.length() != 0) {
            e.setText(text);
        }

        EditText n = binding.notesText;
        EditText q = binding.quantityText;
        EditText d = binding.dateText;
        Button submit = binding.submitItem;
        submit.setOnClickListener(view -> {

            IngredientHandler dbHandler = new IngredientHandler(this);
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                date = dateFormat.parse(d.getText().toString());
            } catch (ParseException ex) {
                // leave it be to use current date
            }
            dbHandler.add(e.getText().toString(), Float.parseFloat(q.getText().toString()), "img1", n.getText().toString(), date, date, "-NS8eopaXJh9mv5HI-Hs",
                    "-NS8eopYaRGPiJGW9w32", "-NS8eopO_GxzgPnsPGBc", "YqhB12pGizP4409dKZ7Fk8i0fNv1");
            dbHandler.getAll();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onGetAllFinish(ArrayList<Ingredient> dataList) {
        System.out.println(dataList);
    }

    @Override
    public void onGetFinish(Ingredient data) {
        System.out.println(data);
    }

    @Override
    public void onDataSuccess(String reason) {
        Toast.makeText(this.getContext(), "Successfully submitted ingredient", Toast.LENGTH_SHORT).show();
        binding.notesText.setText("");
        binding.quantityText.setText("");
        binding.dateText.setText("");
        binding.itemName.setText("");
    }

    @Override
    public void onDataFail(String reason) {
        Toast.makeText(this.getContext(), "Failed to submit ingredient", Toast.LENGTH_SHORT).show();
    }
}