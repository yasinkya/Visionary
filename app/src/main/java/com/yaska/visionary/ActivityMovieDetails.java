package com.yaska.visionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class ActivityMovieDetails extends AppCompatActivity {

    YouTubePlayerView player;
    TextView movieName, description;
    AppCompatButton btn_play, btn_addfav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        player = findViewById(R.id.mini_player);
        movieName = findViewById(R.id.et_movieName);
        description = findViewById(R.id.et_description);
        btn_play = findViewById(R.id.button_play);
        btn_addfav = findViewById(R.id.button_addfav);

    }
}