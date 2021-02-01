package com.example.filmonerim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetails extends AppCompatActivity {

    ImageView movieImage;
    TextView movieName;
    Button playBtn;

    String mNm,mImg,mId,mFU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //tanımlanan değişkenleri activityden bul
        movieImage=findViewById(R.id.movImg);
        movieName=findViewById(R.id.movName);
        playBtn=findViewById(R.id.playButton);

        //  bannerPagerAd. classından veri al
        mNm=getIntent().getStringExtra("movieName");
        mImg=getIntent().getStringExtra("movieImageUrl");
        mId=getIntent().getStringExtra("movieId");
        mFU=getIntent().getStringExtra("movieFileUrl");

        // aktarma
        Glide.with(this).load(mImg).into(movieImage);
        movieName.setText(mNm);

        //  oynatma butonuna bastığında
        playBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent i = new Intent (MovieDetails.this,VideoPlayerActivity.class); // bu classtan videoplayer classına oynatılacak videonun linkini gönder
                i.putExtra("url",mFU);
                startActivity(i);

            }

        });

    }
}