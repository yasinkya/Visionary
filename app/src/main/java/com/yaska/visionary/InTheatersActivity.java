package com.yaska.visionary;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yaska.visionary.adapter.InTheaters.InTheatersRecyclerAdapter;
import com.yaska.visionary.database.MovieDB;
import com.yaska.visionary.model.Actor;
import com.yaska.visionary.model.InTheater;
import com.yaska.visionary.model.Movie;
import com.yaska.visionary.model.Theater;

import java.util.ArrayList;
import java.util.List;

public class InTheatersActivity extends AppCompatActivity {

    MovieDB moviedatabase;

    Theater theater;
    String city, user;
    List<String> movieNames = new ArrayList<>();
    List<Movie> movies = new ArrayList<>();

    RecyclerView recyclerView;
    InTheatersRecyclerAdapter recyclerAdapter;

    TextView cityName, theaterName, theaterAddress, theaterNumber;
    ImageView threeD, dolby, phoneSale, parking, airCond, cafe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_theaters);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        moviedatabase = new MovieDB();

        user = getIntent().getStringExtra("user");
        city = getIntent().getStringExtra("city");
        theater = (Theater) getIntent().getSerializableExtra("theater");


        cityName = findViewById(R.id.cityname);
        theaterName = findViewById(R.id.theatername);
        theaterAddress = findViewById(R.id.theateraddress);
        theaterNumber = findViewById(R.id.theaternumber);

        threeD = findViewById(R.id.icon3d);
        dolby = findViewById(R.id.icondolby);
        phoneSale = findViewById(R.id.iconphonesale);
        parking = findViewById(R.id.iconparking);
        airCond = findViewById(R.id.iconaircon);
        cafe = findViewById(R.id.iconcafe);

        setIconsVisibility();

        cityName.setText(city);
        theaterName.setText(theater.Name);
        theaterAddress.setText(theater.Address);
        theaterNumber.setText(theater.Number);


        if (theater.InTheaters != null){
            for (InTheater movs : theater.InTheaters) {
                movieNames.add(movs.Name);
            }

            setRecycler();

        }


    }

    void setIconsVisibility() {

        if (theater.ThreeD)
            threeD.setVisibility(View.VISIBLE);
        else
            threeD.setVisibility(View.GONE);

        if (theater.Dolby)
            dolby.setVisibility(View.VISIBLE);
        else
            dolby.setVisibility(View.GONE);

        if (theater.PhoneSale)
            phoneSale.setVisibility(View.VISIBLE);
        else
            phoneSale.setVisibility(View.GONE);

        if (theater.Parking)
            parking.setVisibility(View.VISIBLE);
        else
            parking.setVisibility(View.GONE);

        if (theater.AirCond)
            airCond.setVisibility(View.VISIBLE);
        else
            airCond.setVisibility(View.GONE);

        if (theater.Cafe)
            cafe.setVisibility(View.VISIBLE);
        else
            cafe.setVisibility(View.GONE);

    }

    void setRecycler() {

        recyclerView = findViewById(R.id.intheaters_main_recycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        moviedatabase.getMoviesList(movieNames, getMoviesList -> {
            movies = moviedatabase.movieList;

            recyclerAdapter = new InTheatersRecyclerAdapter(this, movies, user);
            recyclerView.setAdapter(recyclerAdapter);
        });

    }
}