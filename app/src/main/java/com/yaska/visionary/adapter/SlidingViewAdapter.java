package com.yaska.visionary.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.yaska.visionary.MovieDetailsActivity;
import com.yaska.visionary.R;
import com.yaska.visionary.model.Movie;
import java.util.List;


// Film özelliklerini başka classlara gönderme
public class SlidingViewAdapter extends PagerAdapter {

    public Context context;
    public List<Movie> movieList;
    public String userName;

    public SlidingViewAdapter(Context context, List<Movie> bannerList, String userName) {
        this.context = context;
        this.movieList = bannerList;
        this.userName = userName;
    }



    @Override
    public int getCount() {
        return movieList.size();
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

        // banner layoutunu izle
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.banner_layout,null);

        // banner layoutundaki banner_image i bul
        ImageView bannerImage = view.findViewById(R.id.banner_image);

        // internetten çekilen resmi banner image'e aktar
        Glide.with(context).load(movieList.get(position).BannerImageUrl).into(bannerImage);
        container.addView(view);

        //film resmine tıkladığında
        bannerImage.setOnClickListener(v -> {

            // movieDetails classına film özelliklerini gönder
            Intent i =new Intent(context, MovieDetailsActivity.class);
            i.putExtra("user", userName);
            i.putExtra("movie", movieList.get(position));

            context.startActivity(i);

        });
        return view;
    }
}
