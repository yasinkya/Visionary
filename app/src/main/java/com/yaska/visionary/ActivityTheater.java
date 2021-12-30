package com.yaska.visionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yaska.visionary.database.TheaterDB;

import java.util.ArrayList;

import java.util.List;

public class ActivityTheater extends AppCompatActivity {

    TheaterDB database;
    List<String> cities = new ArrayList<>();
    ListView citiesListView;
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

        database.getCities(getCities -> {
            cities = database.cities;
            ArrayAdapter<String> citiesAdapter = new ArrayAdapter<>
                    (this, android.R.layout.simple_list_item_1, cities);
            citiesListView.setAdapter(citiesAdapter);
        });

        citiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                citiesListView.setVisibility(View.GONE);
                theaterof.setText(String.format("Movie Theaters of %s", citiesListView.getItemAtPosition(position)));

            }
        });

        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citiesListView.setVisibility(View.VISIBLE);
            }
        });




    }
}