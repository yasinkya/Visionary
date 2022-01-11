package com.yaska.visionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.yaska.visionary.adapter.MovieRecyclerAdapter;
import com.yaska.visionary.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class AllMoviesActivity extends AppCompatActivity {

    String username;
    List<Movie> allmovies;
    RecyclerView recyclerView;
    MovieRecyclerAdapter recyclerAdapter;
    Spinner spinner;
    AppCompatButton btn_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_movies);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        allmovies = (List<Movie>) getIntent().getSerializableExtra("allmovies");
        username = getIntent().getStringExtra("username");

        btn_finish = findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(v -> this.finish());

        spinner = findViewById(R.id.spinner);
        String[] categoriesSpinner = new String[] {
                "All", "Korku", "Aksiyon", "Macera", "Aile", "Komedi", "Animasyon", "Romantik", "Dram", "Suç", "Bilim Kurgu"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categoriesSpinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setRecycler(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /* spinnerdan seçilen kategoriye göre filmleri view'a adaptör ile aktar */
    void setRecycler(String category){
        recyclerView = findViewById(R.id.favorites_main_recycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        if (category.equals("All"))
            recyclerAdapter = new MovieRecyclerAdapter(this, allmovies, username);
        else{
            List<Movie> categoryItems = new ArrayList<>();
            for (Movie m: allmovies) {
                if (m.Genre != null) {
                    if (m.Genre.contains(category)) {
                        categoryItems.add(m);
                    }
                }
            }
            recyclerAdapter = new MovieRecyclerAdapter(this, categoryItems, username);
        }
        recyclerView.setAdapter(recyclerAdapter);

    }


}