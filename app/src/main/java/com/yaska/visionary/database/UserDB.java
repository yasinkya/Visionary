package com.yaska.visionary.database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.yaska.visionary.User;

import java.util.HashMap;
import java.util.Map;

public class UserDB extends DatabaseService {

    Map<String, User> usermap;
    DatabaseReference usersref;
    public String loginResult;

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

    public void check_password(String userName, String pass, final UserDBCallback firebaseCallback){
       ValueEventListener valueEventListener = new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               short userCounter = 0;
               for (DataSnapshot ds: snapshot.getChildren()){
                   if (userName.equals(ds.getKey())){
                       if (pass.equals(ds.child("Account").child("Password").getValue()))
                           loginResult = "ok";
                       else
                           loginResult = "wrongpass";
                   }
                   else
                       userCounter++;
                   if (userCounter == snapshot.getChildrenCount())
                       loginResult = "wronguser";
               }
               firebaseCallback.onUserDBCallback(loginResult);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       };
       usersref.addValueEventListener(valueEventListener);
    }


}


