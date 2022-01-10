package com.yaska.visionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import com.yaska.visionary.adapter.InTheaters.InTheatersRecyclerAdapter;
import com.yaska.visionary.database.UserDB;
import com.yaska.visionary.model.Movie;

public class FavoritesActivity extends AppCompatActivity {

    UserDB userDB;
    String username;
    RecyclerView recyclerView;
    InTheatersRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        userDB = new UserDB();
        username = getIntent().getStringExtra("username");

        setRecycler();

    }

    void setRecycler(){
        recyclerView = findViewById(R.id.favorites_main_recycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        userDB.checkFavMovies(username, getFavMovies -> {
            recyclerAdapter = new InTheatersRecyclerAdapter(this, userDB.favMovies, username);
            recyclerView.setAdapter(recyclerAdapter);
        });
    }

}