package com.example.filmonerim.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.filmonerim.MovieDetails;
import com.example.filmonerim.R;
import com.example.filmonerim.model.CategoryItem;

import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder>{


    Context context;
    List<CategoryItem> categoryItemList;

    public ItemRecyclerAdapter(Context context, List<CategoryItem> categoryItemList) {
        this.context = context;
        this.categoryItemList = categoryItemList;
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
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.cat_recycler_row_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Glide.with(context).load(categoryItemList.get(position).getMovieImgUrl()).into(holder.itemImage);

        holder.itemImage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //Toast.makeText(context,categoryItemList.get(position).getMovieName(),Toast.LENGTH_LONG).show();
                Intent i =new Intent(context, MovieDetails.class);
                i.putExtra("movieId",categoryItemList.get(position).getMovieId());
                i.putExtra("movieName",categoryItemList.get(position).getMovieName());
                i.putExtra("movieImageUrl",categoryItemList.get(position).getMovieImgUrl());
                i.putExtra("movieFileUrl",categoryItemList.get(position).getMovieFileUrl());
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }
}
