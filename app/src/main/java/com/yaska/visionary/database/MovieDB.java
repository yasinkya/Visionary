package com.yaska.visionary.database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.yaska.visionary.model.Actor;
import com.yaska.visionary.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieDB extends DatabaseService{

    DatabaseReference databaseRef;
    public List<Movie> movieList = new ArrayList<>();
    public Movie movie;

    public MovieDB(){
        databaseRef = ref.child("Movies");
    }

    public void getMoviesList(List<String> movieNames, final getMoviesListCallback getMoviesListCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot mov: snapshot.getChildren()){
                    if (movieNames.contains(mov.getKey())){
                        movie = new Movie();
                        movie.Name = mov.getKey();
                        List<Actor> actors = new ArrayList<>();
                        for (DataSnapshot attr:mov.getChildren()){
                            switch (attr.getKey()){
                                case "Banner":
                                    movie.BannerImageUrl = (String) attr.getValue();
                                    break;
                                case "Description":
                                    movie.Description = (String) attr.getValue();
                                    break;
                                case "Süre:":
                                    movie.Time = (String) attr.getValue();
                                    break;
                                case "VideoId":
                                    movie.VideId = (String) attr.getValue();
                                    break;
                                case "Vizyon Tarihi:":
                                    movie.ReleaseDate = (String) attr.getValue();
                                    break;
                                case "Yapımı:":
                                    movie.Producer = (String) attr.getValue();
                                    break;
                                case "Yönetmen:":
                                    movie.Director = (String) attr.getValue();
                                    break;
                                case "Senarist:":
                                    movie.ScreenWriter = (String) attr.getValue();
                                    break;
                                case "Tür:":
                                    movie.Genre = (String) attr.getValue();
                                case "Actors":
                                    for(DataSnapshot act: attr.getChildren()){
                                        actors.add(new Actor((String) act.child("Name").getValue(), (String) act.child("Image").getValue()));
                                    }
                                    movie.Actors = actors;
                                    break;
                            }
                        }
                        movieList.add(movie);
                    }
                }

                getMoviesListCallback.onCallback(movieList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseRef.addListenerForSingleValueEvent(valueEventListener);
    }

}
