package com.yaska.visionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import com.yaska.visionary.adapter.MovieRecyclerAdapter;
import com.yaska.visionary.database.UserDB;

public class FavoritesActivity extends AppCompatActivity {

    /* kullanıcının favorilerini görebileceği sayfa */

    UserDB userDB;
    String username;
    RecyclerView recyclerView;
    MovieRecyclerAdapter recyclerAdapter;

    AppCompatButton btn_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        userDB = new UserDB();
        username = getIntent().getStringExtra("username");

        btn_finish = findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(v -> this.finish());

        setRecycler();

    }

    /* favorileirndeki filmleri veri tabanından çekip view'a movierecycler adapterı ile aktar */
    void setRecycler(){
        recyclerView = findViewById(R.id.favorites_main_recycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        userDB.checkFavMovies(username, getFavMovies -> {
            recyclerAdapter = new MovieRecyclerAdapter(this, userDB.favMovies, username);
            recyclerView.setAdapter(recyclerAdapter);
        });
    }

}