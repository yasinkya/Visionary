package com.yaska.visionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;

import android.widget.ArrayAdapter;

import android.widget.ListView;

import com.yaska.visionary.database.TheaterDB;
import com.yaska.visionary.model.Theater;

import java.text.Collator;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CityTheaterActivity extends AppCompatActivity {

    TheaterDB database;

    String currentCity, currentTheater, user;
    List<String> cities;
    ArrayAdapter<String> adapter;
    public Map<String, Map<String, Theater>> allTheatersMap = new HashMap<>();

    AppCompatButton upButton;
    ListView listViewCities, listViewTheaters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_theater);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        user = getIntent().getStringExtra("user");

        database = new TheaterDB();
        upButton = findViewById(R.id.btnUpcity);
        listViewCities = findViewById(R.id.citiesListView);
        listViewTheaters = findViewById(R.id.theatersListView);


        database.getCityTheaters(getCityTheaters -> {
            allTheatersMap = database.allTheatersMap;
            cities = new ArrayList<>(allTheatersMap.keySet());
            cities.sort((s1, s2) -> {
                Collator collator = Collator.getInstance(new Locale("tr", "TR"));
                return collator.compare(s1, s2);
            });
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities);
            listViewCities.setAdapter(adapter);
        });

        listViewCities.setOnItemClickListener((parent, view, position, id) -> {

            listViewCities.setVisibility(View.GONE);
            currentCity = (String) listViewCities.getItemAtPosition(position);
            upButton.setText(String.format("Movie Theaters of %s", currentCity));

            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1,
                    new ArrayList<>(allTheatersMap.get(currentCity).keySet()));
            listViewTheaters.setAdapter(adapter);

        });

        listViewTheaters.setOnItemClickListener((parent, view, pos, id) ->{
            currentTheater = (String) listViewTheaters.getItemAtPosition(pos);
            changeIntentTo(currentCity,
                    allTheatersMap.get(currentCity).get(currentTheater));

        });

        upButton.setOnClickListener(v -> listViewCities.setVisibility(View.VISIBLE));

    }

    public void changeIntentTo(String currentCity, Theater currentTheater){
        Intent intent = new Intent(CityTheaterActivity.this, InTheatersActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("city", currentCity);
        intent.putExtra("theater", currentTheater);
        startActivity(intent);


    }

    @Override
    public void onBackPressed() {
        if (listViewCities.getVisibility() == View.GONE)
            listViewCities.setVisibility(View.VISIBLE);
        else
            super.onBackPressed();


    }
}