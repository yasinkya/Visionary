package com.yaska.visionary.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yaska.visionary.MovieDetailsActivity;
import com.yaska.visionary.R;
import com.yaska.visionary.model.Movie;
import java.util.List;

public class ItemCategoryAdapter extends RecyclerView.Adapter<ItemCategoryAdapter.ItemViewHolder>{


    Context context;
    List<Movie> categoryItemList;
    public String username;

    public ItemCategoryAdapter(Context context, List<Movie> categoryItemList, String username) {
        this.context = context;
        this.categoryItemList = categoryItemList;
        this.username = username;
    }

    /* Kategoride bulunan filmlerin resimlerini tutmak için imageview'ı yarat */
    public static final class ItemViewHolder extends RecyclerView.ViewHolder{

        ImageView itemImage ;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.category_rec_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {

        /* Veritabanından çekilen herbir filmin resmini internetten çekip glide ile image view'a aktar */
        Glide.with(context).load(categoryItemList.get(position).BannerImageUrl).into(holder.itemImage);

        /* Tıklanan film için MovieDetails sayfasını aç ve filmin verilerini gönder */
        holder.itemImage.setOnClickListener(view -> {
            Intent i =new Intent(context, MovieDetailsActivity.class);
            i.putExtra("user", username);
            i.putExtra("movie", categoryItemList.get(position));
            context.startActivity(i);

        });
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }
}
