package com.yaska.visionary.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yaska.visionary.MovieDetailsActivity;
import com.yaska.visionary.R;
import com.yaska.visionary.model.Actor;
import com.yaska.visionary.model.Movie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MainViewHolder> {

/* Bu adapter filmlerin bazı bilgilerini bir item kart olarak sayfada gösterilmesi için kullanılır */

    Context context;
    String username;
    List<Movie> movieList;

    public MovieRecyclerAdapter(Context context, List<Movie> movieList, String username) {
        this.context = context;
        this.movieList = movieList;
        this.username = username;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.item_intheaters,parent,false));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {

        /* Film item'ında filmin hangi bilgileri gözükecekse onunla ilgili view'ları ayarla */

        holder.movieName.setText(movieList.get(position).Name);
        holder.movieProducer.setText(movieList.get(position).Producer);
        holder.movieGenre.setText(movieList.get(position).Genre);
        holder.movieTime.setText(movieList.get(position).Time);

        if(movieList.get(position).Actors != null){
            List<String> actors = new ArrayList<>();

            for (Actor actname :movieList.get(position).Actors){
                actors.add(actname.Name);
            }
            holder.movieActors.setText(String.join(", ", actors));
        }

        /* Itemda filmin resmini göstermek için glide ile resmin çek ve imageview'a aktar */
        Glide.with(context).load(movieList.get(position).BannerImageUrl).into(holder.movieImage);

        /* Item'ın layoutuna tıklandığında ilgili film için MovieDetails sayfasını aç */
        holder.movieLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra("movie", (Serializable) movieList.get(position));
            intent.putExtra("user", username);
            context.startActivity(intent);

        });

       
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static final class MainViewHolder extends RecyclerView.ViewHolder{

        /* Item'da gözükecek olan viewları tut */

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
