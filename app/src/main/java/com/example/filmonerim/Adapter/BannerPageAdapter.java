package com.example.filmonerim.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.filmonerim.MovieDetails;
import com.example.filmonerim.R;
import com.example.filmonerim.model.Banners;

import java.nio.file.attribute.FileTime;
import java.util.List;


// Film özelliklerini başka classlara gönderme
public class BannerPageAdapter extends PagerAdapter {

    public Context context;
    public List<Banners> bannerList;

    public BannerPageAdapter(Context context, List<Banners> bannerList) {
        this.context = context;
        this.bannerList = bannerList;
    }



    @Override
    public int getCount() {
        return bannerList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.banner_layout,null);  // banner layoutunu izle
        ImageView bannerImage = view.findViewById(R.id.banner_image);   // banner layoutundaki banner_image i bul

        Glide.with(context).load(bannerList.get(position).getMovieImgUrl()).into(bannerImage);  // internetten çekilen resmi banner image'e aktar
        container.addView(view);

        //film resmine tıkladığında
        bannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,bannerList.get(position).getMovieName(),Toast.LENGTH_LONG).show();

                // movieDetails classına film özelliklerini gönder

                Intent i =new Intent(context,MovieDetails.class);
                i.putExtra("movieId",bannerList.get(position).getMovieId());
                i.putExtra("movieName",bannerList.get(position).getMovieName());
                i.putExtra("movieImageUrl",bannerList.get(position).getMovieImgUrl());
                i.putExtra("movieFileUrl",bannerList.get(position).getMovieFileUrl());
                context.startActivity(i);

            }
        });


        return view;

    }
}
