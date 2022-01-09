package com.yaska.visionary;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.WindowManager;
import android.widget.TextView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.yaska.visionary.model.Actor;
import com.yaska.visionary.model.Movie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    YouTubePlayerView player;
    TextView et_movieName, et_movieGenre, et_movieTime, et_movieProd, et_movieDirec,
            et_movieScWrter, et_movieActors, et_movieRsDate, et_description;
    AppCompatButton btn_play, btn_addfav;
    String movieName, movieImage, movieId, movieVideoId;
    Movie currentMovie;
    boolean added_fav;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        player = findViewById(R.id.mini_player);
        et_movieName = findViewById(R.id.et_movieName);
        et_description = findViewById(R.id.et_description);
        et_movieGenre = findViewById(R.id.movie_genre_value);
        et_movieTime = findViewById(R.id.movie_time_value);
        et_movieProd = findViewById(R.id.movie_producer_value);
        et_movieDirec = findViewById(R.id.movie_director_value);
        et_movieScWrter = findViewById(R.id.movie_screenwriter_value);
        et_movieActors = findViewById(R.id.movie_actors_value);
        et_movieRsDate = findViewById(R.id.movie_releasedate_value);

        btn_play = findViewById(R.id.button_play);
        btn_addfav = findViewById(R.id.button_addfav);

        currentMovie = (Movie) getIntent().getSerializableExtra("movie");

//        movieName = getIntent().getStringExtra("movieName");
//        movieImage = getIntent().getStringExtra("movieImageUrl");
//        movieId= getIntent().getStringExtra("movieId");
//        movieVideoId = getIntent().getStringExtra("movieFileUrl");

        et_movieName.setText(currentMovie.Name);
        et_movieGenre.setText(currentMovie.Genre);
        et_movieTime.setText(currentMovie.Time);
        et_movieProd.setText(currentMovie.Producer);
        et_movieDirec.setText(currentMovie.Director);
        et_movieScWrter.setText(currentMovie.ScreenWriter);
        et_movieRsDate.setText(currentMovie.ReleaseDate);
        List<String> actors = new ArrayList<>();
        for (Actor actor : currentMovie.Actors){
            actors.add(actor.Name);
        }
        et_movieActors.setText(String.join(", ", actors));

        et_description.setText(currentMovie.Description);

        // glide internetten resim çekip image viewe aktar
//        Glide.with(this).load(movieImage).into()

        btn_play.setOnClickListener(v ->{
            Intent intent = new Intent(MovieDetailsActivity.this, PlayerActivity.class);
//            intent.putExtra("videoId", currentMovie.VideId);
            intent.putExtra("videoId", "utFoRNkjELo");
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

        player.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo("utFoRNkjELo", 0);
//                todo video id
            }

            @Override
            public void onVideoId(@NonNull YouTubePlayer youTubePlayer, @NonNull String videoId) {
                if (!videoId.equals("utFoRNkjELo"))
                    onReady(youTubePlayer);
                else
                    youTubePlayer.play();
            }
        });


    }
}