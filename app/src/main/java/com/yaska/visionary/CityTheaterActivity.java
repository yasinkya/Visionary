package com.yaska.visionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;

import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yaska.visionary.database.TheaterDB;
import com.yaska.visionary.model.Theater;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityTheaterActivity extends AppCompatActivity {

    TheaterDB database;
    Map<String, List<String>> theatersMap = new HashMap<>();
    Map<String, List<Theater>> cityTheaters = new HashMap<>();

    ArrayAdapter<String> adapter;
    ListView citiesListView, theatersListView;
    AppCompatButton upButton;
    String currentCity;
    LinearLayout theaters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_theater);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        database = new TheaterDB();
        upButton = findViewById(R.id.btnUpcity);
        theaters = findViewById(R.id.layouttheaters);
        citiesListView = findViewById(R.id.citiesListView);
        theatersListView = findViewById(R.id.theatersListView);


        database.getCityTheaters(getCityTheaters -> {
            cityTheaters = database.cityTheaters;
        });

        database.getTheatrsMap(getTheatersMap -> {
            theatersMap = database.theatersMap;
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(theatersMap.keySet()));
            citiesListView.setAdapter(adapter);
        });

        citiesListView.setOnItemClickListener((parent, view, position, id) -> {
            citiesListView.setVisibility(View.GONE);
            currentCity = (String) citiesListView.getItemAtPosition(position);
            upButton.setText(String.format("Movie Theaters of %s", currentCity));
            setTheatersListView(currentCity);

        });

        theatersListView.setOnItemClickListener((parent, view, pos, id) ->
                setIntheatersRecycler(currentCity, (String) theatersListView.getItemAtPosition(pos)));

        upButton.setOnClickListener(v -> citiesListView.setVisibility(View.VISIBLE));




    }
    public void setTheatersListView(String key){
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                theatersMap.get(key));
        theatersListView.setAdapter(adapter);
    }

    public void setIntheatersRecycler(String city, String theater){

        Intent intent = new Intent(CityTheaterActivity.this, InTheatersActivity.class);
        intent.putExtra("city", city);
        intent.putExtra("theater", theater);
        startActivity(intent);

    }
}