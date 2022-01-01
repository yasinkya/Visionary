package com.yaska.visionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yaska.visionary.adapter.InTheaters.InTheatersRecyclerAdapter;
import com.yaska.visionary.model.Actor;
import com.yaska.visionary.model.Movie;
import com.yaska.visionary.model.Theater;

import java.util.ArrayList;
import java.util.List;

public class InTheatersActivity extends AppCompatActivity {

    Theater theater;
    String city;

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

        Toast.makeText(InTheatersActivity.this, city+ theater, Toast.LENGTH_SHORT).show();
        setRecycler();


    }

    void setIconsVisibility(){

        if(theater.ThreeD)
            threeD.setVisibility(View.VISIBLE);
        else
            threeD.setVisibility(View.GONE);

        if(theater.Dolby)
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
            phoneSale.setVisibility(View.GONE);

        if(theater.AirCond)
            airCond.setVisibility(View.VISIBLE);
        else
            airCond.setVisibility(View.GONE);

        if(theater.Cafe)
            cafe.setVisibility(View.VISIBLE);
        else
            cafe.setVisibility(View.GONE);

    }

    void setRecycler(){

        String image = "https://img02.imgsinemalar.com/images/afis_buyuk/a/addams-ailesi-2-1636542332.jpg";
        List<Movie> movies = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();
        List<Actor> actors2 = new ArrayList<>();
        actors.add(new Actor("yaska"));
        actors.add(new Actor("eska"));
        actors.add(new Actor("mahka"));
        actors2.add(new Actor("mahka"));
        actors2.add(new Actor("eheh"));
        actors2.add(new Actor("hüü"));
        movies.add(new Movie("The Maxtix ", "tür", image, "bune", "aaa", actors));
        movies.add(new Movie("İstanbul Muhafızları: Yüzyılın Muhafızları", "tür", image, "bune", "aaa", actors));
        movies.add(new Movie("ooo", "tür", image, "bune", "aaa", actors2));
        movies.add(new Movie("hadi", "tür", image, "bune", "aaa", actors));
        movies.add(new Movie("inşallah", "tür", image, "bune", "aaa", actors2));

        recyclerView = findViewById(R.id.intheaters_main_recycle);
        RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new InTheatersRecyclerAdapter(this, movies);
        recyclerView.setAdapter(recyclerAdapter);
    }
}