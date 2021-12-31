package com.yaska.visionary.adapter.InTheaters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yaska.visionary.MovieDetailsActivity;
import com.yaska.visionary.R;
import com.yaska.visionary.model.Actor;
import com.yaska.visionary.model.CategoryItem;
import com.yaska.visionary.model.Movie;

import java.io.Serializable;
import java.util.List;

public class InTheatersRecyclerAdapter extends RecyclerView.Adapter<InTheatersRecyclerAdapter.MainViewHolder> {



    Context context ;
    List<Movie> movieList;

    public InTheatersRecyclerAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.in_theaters_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.movieName.setText(movieList.get(position).Name);
        holder.movieProducer.setText(movieList.get(position).Producer);
        holder.movieGenre.setText(movieList.get(position).Genre);
        holder.movieTime.setText(movieList.get(position).Time);
        StringBuilder actors = new StringBuilder();
        int i = 1, actsize = movieList.get(position).Actors.size();
        for (Actor actor: movieList.get(position).Actors){
            if (i != actsize){
                actors.append(actor.Name += ", ");
                i ++;
            }
            else
                actors.append(actor.Name);
        }
        Glide.with(context).load(movieList.get(position).BannerImageUrl).into(holder.movieImage);

        holder.movieLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
//            intent.putExtra("movie", (Serializable) movieList.get(position));
            intent.putExtra("movie", "mov");
            context.startActivity(intent);

        });

       
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static final class MainViewHolder extends RecyclerView.ViewHolder{

        TextView movieName, movieTime, movieGenre, movieProducer, movieActors;
        ImageView movieImage;
        ConstraintLayout movieLayout;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.movie_name);
            movieTime = itemView.findViewById(R.id.movie_time_value);
            movieGenre = itemView.findViewById(R.id.movie_genre_value);
            movieProducer = itemView.findViewById(R.id.movie_producer_value);
            movieActors = itemView.findViewById(R.id.movie_actors_value);

            movieImage = itemView.findViewById(R.id.movie_image);

            movieLayout = itemView.findViewById(R.id.movie_layout);

        }
    }


}
