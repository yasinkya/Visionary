package com.yaska.visionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yaska.visionary.database.TheaterDB;

import java.util.ArrayList;

import java.util.List;

public class ActivityTheater extends AppCompatActivity {

    TheaterDB database;
    List<String> cities = new ArrayList<>();
    ListView citiesListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater);

        database = new TheaterDB();
        citiesListView = findViewById(R.id.citiesListView);

        database.getCities(getCities -> {
            cities = database.cities;
            ArrayAdapter<String> citiesAdapter = new ArrayAdapter<>
                    (this, android.R.layout.simple_list_item_1, cities);
            citiesListView.setAdapter(citiesAdapter);
        });




    }
}