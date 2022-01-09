package com.yaska.visionary.database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.yaska.visionary.model.Actor;
import com.yaska.visionary.model.Movie;
import com.yaska.visionary.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDB extends DatabaseService {

    Map<String, User> usermap;
    DatabaseReference usersref;
    public User returnUser;
    public String checkResult, lastUser;
    public List<Movie> favMovies;

    public UserDB(){
        usersref = ref.child("Users");

    }


    public void new_user(User user, String username){
        usermap = new HashMap<>();
        usermap.put("Account", user);
        usersref.child(username).setValue(usermap);
    }

    public void delete_user(String userName){
        usersref.child(userName).removeValue();
    }

    public void update_user(String username, String key, String value){
        usersref.child(username).child("Account").child(key).setValue(value);
    }

    public void changeLastLogin(String username){
        usersref.child("LastLogin").setValue(username);
    }

    public void check_user(String userName, final UserCallback userCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                short userCount = 0;
                for(DataSnapshot ds:snapshot.getChildren()){
                    if (userName.equals(ds.getKey())){
                        returnUser = ds.child("Account").getValue(User.class);
                    }
                    else
                        userCount++;
                    if (userCount == snapshot.getChildrenCount())
                        returnUser = null;

                }
                userCallback.onCallback(returnUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        usersref.addValueEventListener(valueEventListener);
    }

    public void checkUserPass(String username, String pass, final CheckPassCallback checkPassCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                short userCount = 0;
                for(DataSnapshot ds:snapshot.getChildren()){
                    if (username.equals(ds.getKey())){
                        if (pass.equals(ds.child("Account").child("Password").getValue()))
                            checkResult = "ok";
                        else
                            checkResult = "wp";
                    }
                    else
                        userCount++;
                    if (userCount == snapshot.getChildrenCount())
                        checkResult = "none";

                }
                checkPassCallback.onCallBack(checkResult);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };

        usersref.addListenerForSingleValueEvent(valueEventListener);
    }

    public void getUser(String username, final GetUserCallback getUserCallback){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                returnUser = snapshot.child(username).child("Account").getValue(User.class);
                getUserCallback.onCallback(returnUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        usersref.addListenerForSingleValueEvent(valueEventListener);
    }
    
    public void getLastUser(final GetLastUserCallback getLastUserCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lastUser = (String) snapshot.child("LastLogin").getValue();
                getLastUserCallback.onCallBack(lastUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        usersref.addListenerForSingleValueEvent(valueEventListener);
    }

    public void addFavMovie(String username, Movie movie){
        usersref.child(username).child("favorites").child(movie.Name).setValue(movie);
    }
    public void removeFavMovie(String username, String movieName){
        usersref.child(username).child("favorites").child(movieName).removeValue();
    }
    public void checkFavMovies(String username, GetFavMoviesCallback getFavMoviesCallback){
        favMovies = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mov : snapshot.getChildren()){
                    favMovies.add(mov.getValue(Movie.class));
                }
                getFavMoviesCallback.onCallback(favMovies);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        usersref.child(username).child("favorites").addValueEventListener(valueEventListener);
    }
}

