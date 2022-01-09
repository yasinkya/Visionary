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
import com.yaska.visionary.LoginActivity;
import com.yaska.visionary.MovieDetailsActivity;
import com.yaska.visionary.R;
import com.yaska.visionary.model.CategoryItem;
import com.yaska.visionary.model.Movie;
import com.yaska.visionary.model.User;

import java.io.Serializable;
import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder>{


    Context context;
    List<Movie> categoryItemList;
    public String user;

    public ItemRecyclerAdapter(Context context, List<Movie> categoryItemList, String user) {
        this.context = context;
        this.categoryItemList = categoryItemList;
        this.user = user;
    }

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
        Glide.with(context).load(categoryItemList.get(position).BannerImageUrl).into(holder.itemImage);

        holder.itemImage.setOnClickListener(view -> {
            Intent i =new Intent(context, MovieDetailsActivity.class);
            i.putExtra("user", user);
            i.putExtra("movie", (Serializable) categoryItemList.get(position));
            context.startActivity(i);

        });
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }
}
