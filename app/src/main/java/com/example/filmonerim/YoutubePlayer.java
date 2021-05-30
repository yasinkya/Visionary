package com.example.filmonerim;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class YoutubePlayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        YouTubePlayerView player = findViewById(R.id.ytbPlayer);
        getLifecycle().addObserver(player);

        player.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {

            @Override
            public void onVideoId(YouTubePlayer youTubePlayer, String videoId) {
                //https://www.youtube.com/watch?v=1mRXuSPdddc
                if(!videoId.equals("1mRXuSPdddc"))
                    onReady(youTubePlayer);
                else
                    youTubePlayer.play();
            }


            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo("1mRXuSPdddc",0);

            }
        });
    }
}