package com.yaska.visionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.yaska.visionary.model.Movie;

import java.io.Serializable;
import java.util.List;

public class AllMoviesActivity extends AppCompatActivity {

    Serializable allmovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_movies);


        allmovies = getIntent().getSerializableExtra("allmovies");

    }
}