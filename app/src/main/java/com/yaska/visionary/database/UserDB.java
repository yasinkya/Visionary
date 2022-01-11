package com.yaska.visionary.database;

import androidx.annotation.NonNull;

import com.google.android.gms.common.UserRecoverableException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.platforminfo.UserAgentPublisher;
import com.yaska.visionary.model.Actor;
import com.yaska.visionary.model.Movie;
import com.yaska.visionary.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDB extends DatabaseService {

    /* Kullanıcı veri tabanı kullanıcını bilgilerini güncellemeye, silmeyei bilgilier getirmeye yarar*/

    Map<String, User> usermap;
    DatabaseReference usersref;
    public User returnUser;
    public String checkResult, lastUser;
    public List<Movie> favMovies;

    public UserDB(){
        usersref = ref.child("Users");

    }

    /* login sayfasında yeni bir kullanıcının kaydını veri tabanına aktarır*/
    public void new_user(User user, String username){
        usermap = new HashMap<>();
        usermap.put("Account", user);
        usersref.child(username).setValue(usermap);
    }

    /* tüm hesap bilgilerini siler */
    public void delete_user(String userName){
        usersref.child(userName).removeValue();
    }

    /* kullanıcı şifresini sıfırlar */
    public void reset_password(String username, String newpass){
        usersref.child(username).child("Account").child("Password").setValue(newpass);
    }

    /* kullanıcının tüm bilgilerini günceller */
    public void update_user(User user){
        usersref.child(user.UserName).child("Account").setValue(user);
    }

    /* uygulamaya giriş yapan son kullanıcının kullanıcı adını veri tabanına kaydeder
    kullanıcı uygulamayı tekrar açtığında giriş yapmış olarak karşılanır
     */
    public void changeLastLogin(String username){
        usersref.child("LastLogin").setValue(username);
    }

    /* son giriş yapan kullanıcıyı geri dönder */
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

    /* girilern kullanıcı adı veritabanında ver mı kontrol edilir
    şifre sıfırlama için
     */
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

    /* giriş yapmaya çalışan kullanıcın kullancı adı ve şifresi kontrol edilir ve geri dönülür */
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

    /* giriş yapan kullanıcın bilgisi geri dönderilir*/
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

    /* kullanıcı filmleri favorilerine ekler*/
    public void addFavMovie(String username, Movie movie){
        usersref.child(username).child("favorites").child(movie.Name).setValue(movie);
    }
    /* favorilerinden çıkarır*/
    public void removeFavMovie(String username, String movieName){
        usersref.child(username).child("favorites").child(movieName).removeValue();
    }
    /* favorilerine eklediği filmleir geri dönder*/
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

