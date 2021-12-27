package com.yaska.visionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class ActivityMovieDetails extends AppCompatActivity {

    YouTubePlayerView player;
    TextView et_movieName, et_description;
    AppCompatButton btn_play, btn_addfav;
    String movieName, movieImage, movieId, movieVideoId;
    boolean added_fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        player = findViewById(R.id.mini_player);
        et_movieName = findViewById(R.id.et_movieName);
        et_description = findViewById(R.id.et_description);
        btn_play = findViewById(R.id.button_play);
        btn_addfav = findViewById(R.id.button_addfav);

        movieName = getIntent().getStringExtra("movieName");
        movieImage = getIntent().getStringExtra("movieImageUrl");
        movieId= getIntent().getStringExtra("movieId");
        movieVideoId = getIntent().getStringExtra("movieFileUrl");

        et_movieName.setText(movieName);
        et_description.setText("deneme description");

        // glide internetten resim çekip image viewe aktar
//        Glide.with(this).load(movieImage).into()

        btn_play.setOnClickListener(v ->{
            Intent intent = new Intent(ActivityMovieDetails.this, ActivityPlayer.class);
            intent.putExtra("videoId", movieVideoId);
            startActivity(intent);

        });

        btn_addfav.setOnClickListener(v ->{
            //todo add to users favorites database
            // ref.child("favorites").child(moviename).setvalue(movieclass)
//            btn_addfav.setBackground(R.drawable.icon_addedfav);
            if (!added_fav){
                btn_addfav.setBackgroundResource(R.drawable.icon_addedfav);
                added_fav = true;
            }
            else{
                btn_addfav.setBackgroundResource(R.drawable.icon_addfav);
                added_fav = false;
            }


        });


    }
}