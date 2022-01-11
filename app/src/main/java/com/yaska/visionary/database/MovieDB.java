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

    /* Bu databse child'ı veritabanındaki filmleri getimek için kullanılır */

    DatabaseReference databaseRef;
    public Movie movie;
    public List<Movie> movieList = new ArrayList<>();

    public MovieDB(){
        databaseRef = ref.child("Movies");
    }

    /* Bu fonksiyon sadece isteniler, isimleri bilinen filmleri veritabanından çeker */
    public void getMoviesList(List<String> movieNames, final GetMoviesListCallback getMoviesListCallback){

        /* Listener ayarla */
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                /* bu listener için veritabaındaki filmlerin tamamını gez */
                for (DataSnapshot mov: snapshot.getChildren()){

                    /* Sırasıyla gezilen filmler için filmin adı, istenilen film listesinde varsa */
                    if (movieNames.contains(mov.getKey())){

                        /* Yeni bir Movie nesnesi yarat ve o nesnenin özelliklerini doldur */
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
                                case "Süre":
                                    movie.Time = (String) attr.getValue();
                                    break;
                                case "VideoId":
                                    movie.VideId = (String) attr.getValue();
                                    break;
                                case "Vizyon Tarihi":
                                    movie.ReleaseDate = (String) attr.getValue();
                                    break;
                                case "Yapımı":
                                    movie.Producer = (String) attr.getValue();
                                    break;
                                case "Yönetmen":
                                    movie.Director = (String) attr.getValue();
                                    break;
                                case "Senarist":
                                    movie.ScreenWriter = (String) attr.getValue();
                                    break;
                                case "Tür":
                                    movie.Genre = (String) attr.getValue();
                                case "Actors":
                                    for(DataSnapshot act: attr.getChildren()){
                                        actors.add(new Actor((String) act.child("Name").getValue(), (String) act.child("Image").getValue()));
                                    }
                                    movie.Actors = actors;
                                    break;
                            }
                        }

                        /* Özellikleri ayarlanan nesneyi geri dönderilecek olan listeye aktar */
                        movieList.add(movie);
                    }
                }

                /* DatabaseService classındaki Callback'i çağır */
                getMoviesListCallback.onCallback(movieList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseRef.addListenerForSingleValueEvent(valueEventListener);
    }

    /* Bu fonksiyon veritabından tüm filmleri bir liste olarak çeker */
    public void retMoviesList(final GetMoviesListCallback getMoviesListCallback){

        /* Listener ayarla */
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                /* Veritabnındaki her bir filmi gez */

                for (DataSnapshot mov: snapshot.getChildren()){

                    /* yeni bir movie nesnesi oluştur özelliklerini ayarla ve geri dönderilecek listeye aktar */
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
                            case "Süre":
                                movie.Time = (String) attr.getValue();
                                break;
                            case "VideoId":
                                movie.VideId = (String) attr.getValue();
                                break;
                            case "Vizyon Tarihi":
                                movie.ReleaseDate = (String) attr.getValue();
                                break;
                            case "Yapımı":
                                movie.Producer = (String) attr.getValue();
                                break;
                            case "Yönetmen":
                                movie.Director = (String) attr.getValue();
                                break;
                            case "Senarist":
                                movie.ScreenWriter = (String) attr.getValue();
                                break;
                            case "Tür":
                                movie.Genre = (String) attr.getValue();
                                break;
                            case "Tür:":
                                movie.Genre = (String) attr.getValue();
                                break;
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

                /* Callback'i çağır */
                getMoviesListCallback.onCallback(movieList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseRef.addListenerForSingleValueEvent(valueEventListener);
    }


}
