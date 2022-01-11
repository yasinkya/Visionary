package com.yaska.visionary;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yaska.visionary.adapter.MovieRecyclerAdapter;
import com.yaska.visionary.database.MovieDB;
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
    MovieRecyclerAdapter recyclerAdapter;

    TextView tv_cityName, tv_theaterName, tv_theaterAddress, tv_theaterNumber;
    ImageView iv_threeD, iv_dolby, iv_phoneSale, iv_parking, iv_airCond, iv_cafe;


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


        tv_cityName = findViewById(R.id.cityname);
        tv_theaterName = findViewById(R.id.theatername);
        tv_theaterAddress = findViewById(R.id.theateraddress);
        tv_theaterNumber = findViewById(R.id.theaternumber);

        iv_threeD = findViewById(R.id.icon3d);
        iv_dolby = findViewById(R.id.icondolby);
        iv_phoneSale = findViewById(R.id.iconphonesale);
        iv_parking = findViewById(R.id.iconparking);
        iv_airCond = findViewById(R.id.iconaircon);
        iv_cafe = findViewById(R.id.iconcafe);

        setIconsVisibility();

        tv_cityName.setText(city);
        tv_theaterName.setText(theater.Name);
        tv_theaterAddress.setText(theater.Address);
        tv_theaterNumber.setText(theater.Number);


        if (theater.InTheaters != null){
            for (InTheater movs : theater.InTheaters) {
                movieNames.add(movs.Name);
            }

            setRecycler();

        }


    }

    void setIconsVisibility() {

        if (theater.ThreeD)
            iv_threeD.setVisibility(View.VISIBLE);
        else
            iv_threeD.setVisibility(View.GONE);

        if (theater.Dolby)
            iv_dolby.setVisibility(View.VISIBLE);
        else
            iv_dolby.setVisibility(View.GONE);

        if (theater.PhoneSale)
            iv_phoneSale.setVisibility(View.VISIBLE);
        else
            iv_phoneSale.setVisibility(View.GONE);

        if (theater.Parking)
            iv_parking.setVisibility(View.VISIBLE);
        else
            iv_parking.setVisibility(View.GONE);

        if (theater.AirCond)
            iv_airCond.setVisibility(View.VISIBLE);
        else
            iv_airCond.setVisibility(View.GONE);

        if (theater.Cafe)
            iv_cafe.setVisibility(View.VISIBLE);
        else
            iv_cafe.setVisibility(View.GONE);

    }

    /* sinema salonundaki vizyondaki filmler isimleri listesine göre veri tabanından ilgili filmleri
    çek ve view'a movierecycler adapter ile aktar
     */
    void setRecycler() {
        recyclerView = findViewById(R.id.intheaters_main_recycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        moviedatabase.getMoviesList(movieNames, getMoviesList -> {
            movies = moviedatabase.movieList;

            recyclerAdapter = new MovieRecyclerAdapter(this, movies, user);
            recyclerView.setAdapter(recyclerAdapter);
        });

    }
}