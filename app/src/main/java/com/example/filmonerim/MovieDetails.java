package com.example.filmonerim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MovieDetails extends AppCompatActivity {

    ImageView movieImage;
    TextView movieName;
    Button playBtn,favBtn;

    String mNm,mImg,mId,mFU,uKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //Full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //tanımlanan değişkenleri activityden bul
        movieImage=findViewById(R.id.movImg);
        movieName=findViewById(R.id.movName);
        playBtn=findViewById(R.id.playButton);
        favBtn=findViewById(R.id.favButton);

        //  bannerPagerAd. classından veri al
        mNm=getIntent().getStringExtra("movieName");
        mImg=getIntent().getStringExtra("movieImageUrl");
        mId=getIntent().getStringExtra("movieId");
        mFU=getIntent().getStringExtra("movieFileUrl");


        // aktarma
        Glide.with(this).load(mImg).into(movieImage);
        movieName.setText(uKey);

        //  oynatma butonuna bastığında
        playBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent i = new Intent (MovieDetails.this,VideoPlayerActivity.class); // bu classtan videoplayer classına oynatılacak videonun linkini gönder
                i.putExtra("url",mFU);
                startActivity(i);

            }

        });

        //favorilere Ekle
        favBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
                Favorites fav= new Favorites(mFU,mImg,mNm);
                dbRef.child("Users").child(uKey).child("Favorites").child("2342").setValue("fav");
            }
        });

    }
}