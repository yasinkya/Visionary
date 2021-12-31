package com.yaska.visionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.yaska.visionary.adapter.InTheaters.InTheatersRecyclerAdapter;
import com.yaska.visionary.model.Actor;
import com.yaska.visionary.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class InTheatersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    InTheatersRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_theaters);

        String city = getIntent().getStringExtra("city");
        String theater = getIntent().getStringExtra("theater");

        Toast.makeText(InTheatersActivity.this, city+ theater, Toast.LENGTH_SHORT).show();
        setRecycler();


    }

    public void setRecycler(){

        List<Movie> movies = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();
        String image = "https://img02.imgsinemalar.com/images/afis_buyuk/a/addams-ailesi-2-1636542332.jpg";
        actors.add(new Actor("yaska"));
        actors.add(new Actor("eska"));
        actors.add(new Actor("mahka"));
        movies.add(new Movie("film1", "tür", image, "bune", "aaa", actors));
        movies.add(new Movie("anaa", "tür", image, "bune", "aaa", actors));
        movies.add(new Movie("ooo", "tür", image, "bune", "aaa", actors));
        movies.add(new Movie("hadi", "tür", image, "bune", "aaa", actors));
        movies.add(new Movie("inşallah", "tür", image, "bune", "aaa", actors));

        recyclerView = findViewById(R.id.intheaters_main_recycle);
        RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new InTheatersRecyclerAdapter(this, movies);
        recyclerView.setAdapter(recyclerAdapter);
    }
}