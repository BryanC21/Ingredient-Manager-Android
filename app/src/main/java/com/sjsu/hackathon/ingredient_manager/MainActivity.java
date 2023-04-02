package com.sjsu.hackathon.ingredient_manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.sjsu.hackathon.ingredient_manager.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DataListener {
    private ActivityMainBinding binding;
    public static String country = "US";
    String DB_PATH;
    final Context context=this;
    private SQLiteDatabase mDataBase;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FetchData fd = new FetchData();

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

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fd.allowedCountries);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                  String cn = parent.getItemAtPosition(position).toString();
                                                  System.out.println(cn + " selected");
                                                  country = cn;
                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> parent) {
                                              }
                                          });
//        fd.getData(new DBHandler(this),"FDI Inflows", "1980", "2021", "US", this);
        AnnotationDBHandler ad = new AnnotationDBHandler(this);
//        ad.addNewData("title5", "body5");
        System.out.println(ad.getDataList());
//        System.out.println(ad.getDataById(1));
//        System.out.println(ad.getDataById(2));
//        System.out.println(ad.getDataById(3));
    }

    @Override
    public void onDataFinish(ArrayList<Data> dataList) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(dataList);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    @Override
    public void onDataFail(String reason) {
        System.out.println(reason);
    }
}