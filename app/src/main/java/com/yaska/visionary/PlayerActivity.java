package com.yaska.visionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class PlayerActivity extends AppCompatActivity {

    String mvideoId;
    YouTubePlayerView player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mvideoId = getIntent().getStringExtra("videoId");

        player = findViewById(R.id.player);
        getLifecycle().addObserver(player);


        player.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onVideoId(@NonNull YouTubePlayer youTubePlayer, @NonNull String videoId) {
                if(!videoId.equals(mvideoId))
                    onReady(youTubePlayer);
                else
                    youTubePlayer.play();
            }

            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(mvideoId, 0);
            }
        });

    }
}