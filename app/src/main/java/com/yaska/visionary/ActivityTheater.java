package com.yaska.visionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yaska.visionary.database.TheaterDB;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityTheater extends AppCompatActivity {

    TheaterDB database;
    Map<String, List<String>> theatersMap = new HashMap<>();
    ArrayAdapter<String> adapter;
    ListView citiesListView, theatersListView;
    AppCompatButton upButton;
    TextView theaterof;

    LinearLayout theaters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater);

        database = new TheaterDB();
        upButton = findViewById(R.id.btnUp);
        theaterof = findViewById(R.id.et_theaterof);
        theaters = findViewById(R.id.layouttheaters);
        citiesListView = findViewById(R.id.citiesListView);
        theatersListView = findViewById(R.id.theatersListView);


        database.getTheatrsMap(getTheatersMap -> {
            theatersMap = database.theatersMap;
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(theatersMap.keySet()));
            citiesListView.setAdapter(adapter);
        });

        citiesListView.setOnItemClickListener((parent, view, position, id) -> {
            citiesListView.setVisibility(View.GONE);
            String key = (String) citiesListView.getItemAtPosition(position);
            theaterof.setText(String.format("Movie Theaters of %s", key));
            setTheatersListView(key);

        });

        upButton.setOnClickListener(v -> citiesListView.setVisibility(View.VISIBLE));




    }
    public void setTheatersListView(String key){
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                theatersMap.get(key));
        theatersListView.setAdapter(adapter);
    }
}