package com.sjsu.hackathon.ingredient_manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.sjsu.hackathon.ingredient_manager.controller.Chatgpt;
import com.sjsu.hackathon.ingredient_manager.controller.RecipeController;
import com.sjsu.hackathon.ingredient_manager.data.listener.RecipeListener;
import com.sjsu.hackathon.ingredient_manager.data.model.Ingredient;
import com.sjsu.hackathon.ingredient_manager.data.model.Recipe;
import com.sjsu.hackathon.ingredient_manager.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipeListener {

    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // Check if the result was successful
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Get the data from the Intent extras
                        Intent data = result.getData();
                        Bundle bundle = data.getExtras();
                        if (bundle != null) {
                            String value = bundle.getString("label");
                            System.out.println(value);
                            TextView textView = findViewById(R.id.itemName);
                            textView.setText(value);
                            // Use the data as needed
                        }
                    }
                });

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    public ActivityResultLauncher<Intent> openCamera() {

        Intent intent = new Intent(this, CameraActivity.class);
        //startActivity(intent);
        launcher.launch(intent);
        return launcher;

    }


    @Override
    public void onDataSuccess(String reason) {

    }

    @Override
    public void onDataFail(String reason) {

    }

    @Override
    public void onGetSuccess(ArrayList<Recipe> recipeList) {
        System.out.println(recipeList);
    }
}