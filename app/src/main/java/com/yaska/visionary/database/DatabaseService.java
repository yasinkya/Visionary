package com.yaska.visionary.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yaska.visionary.model.Movie;
import com.yaska.visionary.model.Theater;
import com.yaska.visionary.model.User;

import java.util.List;
import java.util.Map;

public class DatabaseService {

    /* Genel database bağlantısı, referansı ve verilerin çekilmesiyle ilgili Callback interface'leri */

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    public interface UserCallback {
        void onCallback(User returnUser);
    }

    public interface CheckPassCallback {
        void onCallBack(String result);
    }

    public interface GetUserCallback{
        void onCallback(User getUser);
    }
    
    public interface GetLastUserCallback {
        void onCallBack(String getLastUser);
    }

    public interface GetCityTheatersCallback{
        void onCallback(Map<String, Map<String, Theater>> getCityTheaters);
    }

    public interface GetMoviesListCallback {
        void onCallback(List<Movie> getMoviesList);
    }

    public interface GetFavMoviesCallback {
        void onCallback(List<Movie> getFavMovies);
    }

}
