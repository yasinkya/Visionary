package com.yaska.visionary;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import com.yaska.visionary.database.UserDB;
import com.yaska.visionary.model.Movie;


public class MovieDetailsActivity extends AppCompatActivity {

    YouTubePlayerView player;
    TextView et_movieName, et_movieGenre, et_movieTime, et_movieProd, et_movieDirec,
            et_movieScWrter, et_movieActors, et_movieRsDate, et_description,
            et_actor1, et_actor2, et_actor3, et_actor4, et_actor5, et_actor6, et_actor7, et_actor8;
    ImageView im_actor1, im_actor2, im_actor3, im_actor4, im_actor5, im_actor6, im_actor7, im_actor8;
    CardView layim1, layim2, layim3, layim4, layim5, layim6, layim7, layim8;
    AppCompatButton btn_play, btn_addfav;
    Movie currentMovie;
    String username, videoId;
    UserDB userDB = new UserDB();
    boolean added_fav;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        username = getIntent().getStringExtra("user");
        currentMovie = (Movie) getIntent().getSerializableExtra("movie");


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

        layim1 = findViewById(R.id.lay_actor_image1);
        layim2 = findViewById(R.id.lay_actor_image2);
        layim3 = findViewById(R.id.lay_actor_image3);
        layim4 = findViewById(R.id.lay_actor_image4);
        layim5 = findViewById(R.id.lay_actor_image5);
        layim6 = findViewById(R.id.lay_actor_image6);
        layim7 = findViewById(R.id.lay_actor_image7);
        layim8 = findViewById(R.id.lay_actor_image8);

        et_actor1 = findViewById(R.id.actor_name1);
        et_actor2 = findViewById(R.id.actor_name2);
        et_actor3 = findViewById(R.id.actor_name3);
        et_actor4 = findViewById(R.id.actor_name4);
        et_actor5 = findViewById(R.id.actor_name5);
        et_actor6 = findViewById(R.id.actor_name6);
        et_actor7 = findViewById(R.id.actor_name7);
        et_actor8 = findViewById(R.id.actor_name8);

        im_actor1 = findViewById(R.id.actor_image1);
        im_actor2 = findViewById(R.id.actor_image2);
        im_actor3 = findViewById(R.id.actor_image3);
        im_actor4 = findViewById(R.id.actor_image4);
        im_actor5 = findViewById(R.id.actor_image5);
        im_actor6 = findViewById(R.id.actor_image6);
        im_actor7 = findViewById(R.id.actor_image7);
        im_actor8 = findViewById(R.id.actor_image8);

        btn_play = findViewById(R.id.button_play);
        btn_addfav = findViewById(R.id.button_addfav);

        hideAllActorViews();
        checkMovieIsFav();

        et_movieName.setText(currentMovie.Name);
        et_movieGenre.setText(currentMovie.Genre);
        et_movieTime.setText(currentMovie.Time);
        et_movieProd.setText(currentMovie.Producer);
        et_movieDirec.setText(currentMovie.Director);
        et_movieScWrter.setText(currentMovie.ScreenWriter);
        et_movieRsDate.setText(currentMovie.ReleaseDate);
        et_description.setText(currentMovie.Description);
        if (currentMovie.Actors != null){
            for (int i = 0; i < currentMovie.Actors.size(); i++){
                switch (i){
                    case 0:
                        Glide.with(this).load(currentMovie.Actors.get(i).ImageUrl).into(im_actor1);
                        et_actor1.setText(currentMovie.Actors.get(i).Name);
                        et_actor1.setVisibility(View.VISIBLE);
                        im_actor1.setVisibility(View.VISIBLE);
                        layim1.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        Glide.with(this).load(currentMovie.Actors.get(i).ImageUrl).into(im_actor2);
                        et_actor2.setText(currentMovie.Actors.get(i).Name);
                        et_actor2.setVisibility(View.VISIBLE);
                        im_actor2.setVisibility(View.VISIBLE);
                        layim2.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        Glide.with(this).load(currentMovie.Actors.get(i).ImageUrl).into(im_actor3);
                        et_actor3.setText(currentMovie.Actors.get(i).Name);
                        et_actor3.setVisibility(View.VISIBLE);
                        im_actor3.setVisibility(View.VISIBLE);
                        layim3.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        Glide.with(this).load(currentMovie.Actors.get(i).ImageUrl).into(im_actor4);
                        et_actor4.setText(currentMovie.Actors.get(i).Name);
                        et_actor4.setVisibility(View.VISIBLE);
                        im_actor4.setVisibility(View.VISIBLE);
                        layim4.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        Glide.with(this).load(currentMovie.Actors.get(i).ImageUrl).into(im_actor5);
                        et_actor5.setText(currentMovie.Actors.get(i).Name);
                        et_actor5.setVisibility(View.VISIBLE);
                        im_actor5.setVisibility(View.VISIBLE);
                        layim5.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        Glide.with(this).load(currentMovie.Actors.get(i).ImageUrl).into(im_actor6);
                        et_actor6.setText(currentMovie.Actors.get(i).Name);
                        et_actor6.setVisibility(View.VISIBLE);
                        im_actor6.setVisibility(View.VISIBLE);
                        layim6.setVisibility(View.VISIBLE);
                        break;
                    case 6:
                        Glide.with(this).load(currentMovie.Actors.get(i).ImageUrl).into(im_actor7);
                        et_actor7.setText(currentMovie.Actors.get(i).Name);
                        et_actor7.setVisibility(View.VISIBLE);
                        im_actor7.setVisibility(View.VISIBLE);
                        layim7.setVisibility(View.VISIBLE);
                        break;
                    case 7:
                        Glide.with(this).load(currentMovie.Actors.get(i).ImageUrl).into(im_actor8);
                        et_actor8.setText(currentMovie.Actors.get(i).Name);
                        et_actor8.setVisibility(View.VISIBLE);
                        im_actor8.setVisibility(View.VISIBLE);
                        layim8.setVisibility(View.VISIBLE);
                        break;

                }
            }
        }

        if (currentMovie.VideId != null)
            videoId = currentMovie.VideId;
        else
            videoId = "";

        btn_play.setOnClickListener(v ->{
            Intent intent = new Intent(MovieDetailsActivity.this, PlayerActivity.class);
            intent.putExtra("videoId", currentMovie.VideId);
            startActivity(intent);

        });

        btn_addfav.setOnClickListener(v ->{
            if (!added_fav){
                userDB.addFavMovie(username, currentMovie);
                btn_addfav.setBackgroundResource(R.drawable.icon_addedfav);
                added_fav = true;
            }
            else{
                userDB.removeFavMovie(username, currentMovie.Name);
                btn_addfav.setBackgroundResource(R.drawable.icon_addfav);
                added_fav = false;
            }
        });

        player.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(currentMovie.VideId, 0);
            }

            @Override
            public void onVideoId(@NonNull YouTubePlayer youTubePlayer, @NonNull String videoId) {
                if (!videoId.equals(currentMovie.VideId))
                    onReady(youTubePlayer);
                else
                    youTubePlayer.play();
            }
        });
        
    }
    public void hideAllActorViews(){

        layim1.setVisibility(View.GONE);
        layim2.setVisibility(View.GONE);
        layim3.setVisibility(View.GONE);
        layim4.setVisibility(View.GONE);
        layim5.setVisibility(View.GONE);
        layim6.setVisibility(View.GONE);
        layim7.setVisibility(View.GONE);
        layim8.setVisibility(View.GONE);

        et_actor8.setVisibility(View.GONE);
        im_actor8.setVisibility(View.GONE);

        et_actor7.setVisibility(View.GONE);
        im_actor7.setVisibility(View.GONE);

        et_actor6.setVisibility(View.GONE);
        im_actor6.setVisibility(View.GONE);

        et_actor5.setVisibility(View.GONE);
        im_actor5.setVisibility(View.GONE);

        et_actor4.setVisibility(View.GONE);
        im_actor4.setVisibility(View.GONE);

        et_actor3.setVisibility(View.GONE);
        im_actor3.setVisibility(View.GONE);

        et_actor2.setVisibility(View.GONE);
        im_actor2.setVisibility(View.GONE);

        et_actor1.setVisibility(View.GONE);
        im_actor1.setVisibility(View.GONE);

    }

    public void checkMovieIsFav(){
        userDB.checkFavMovies(username, getFavMovies -> {
            for (Movie mov : userDB.favMovies){
                if(currentMovie.Name.equals(mov.Name)){
                    btn_addfav.setBackgroundResource(R.drawable.icon_addedfav);
                    added_fav = true;
                }
            }
        });

    }
}