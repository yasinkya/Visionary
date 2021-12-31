package com.yaska.visionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yaska.visionary.adapter.InTheaters.InTheatersRecyclerAdapter;
import com.yaska.visionary.database.TheaterDB;
import com.yaska.visionary.model.Actor;
import com.yaska.visionary.model.Movie;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityTheaterActivity extends AppCompatActivity {

    TheaterDB database;
    Map<String, List<String>> theatersMap = new HashMap<>();
    ArrayAdapter<String> adapter;
    ListView citiesListView, theatersListView;
    AppCompatButton upButton;
    String currentCity;
    RecyclerView recyclerView;
    InTheatersRecyclerAdapter recyclerAdapter;

    LinearLayout theaters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_theater);

        database = new TheaterDB();
        upButton = findViewById(R.id.btnUpcity);
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
            currentCity = (String) citiesListView.getItemAtPosition(position);
            upButton.setText(String.format("Movie Theaters of %s", currentCity));
            setTheatersListView(currentCity);

        });

        theatersListView.setOnItemClickListener((parent, view, pos, id) -> {
            setIntheatersRecycler(currentCity, (String) theatersListView.getItemAtPosition(pos));
        });

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

//        List<Movie> movies = new ArrayList<>();
//        List<Actor> actors = new ArrayList<>();
//        String image = "https://img02.imgsinemalar.com/images/afis_buyuk/a/addams-ailesi-2-1636542332.jpg";
//        actors.add(new Actor("yaska"));
//        actors.add(new Actor("eska"));
//        actors.add(new Actor("mahka"));
//        movies.add(new Movie("film1", "tür", image, "bune", "aaa", actors));
//        movies.add(new Movie("anaa", "tür", image, "bune", "aaa", actors));
//        movies.add(new Movie("ooo", "tür", image, "bune", "aaa", actors));
//        movies.add(new Movie("hadi", "tür", image, "bune", "aaa", actors));
//        movies.add(new Movie("inşallah", "tür", image, "bune", "aaa", actors));
//
//        recyclerView = findViewById(R.id.intheaters_main_recycle);
//        RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerAdapter = new InTheatersRecyclerAdapter(this, movies);
//        recyclerView.setAdapter(recyclerAdapter);

    }
}