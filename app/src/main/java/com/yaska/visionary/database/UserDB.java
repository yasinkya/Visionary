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
    public User returnUser;
    public String loginResult;
    public String yaska;

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

    public void check_passw(String userName, final UserDBCallback userDBCallback){
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
               userDBCallback.onCallback(returnUser);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       };

       usersref.addValueEventListener(valueEventListener);
    }

//    public void check_password(String userName, String pass, final UserDBCallback firebaseCallback){
//       ValueEventListener valueEventListener = new ValueEventListener() {
//           @Override
//           public void onDataChange(@NonNull DataSnapshot snapshot) {
//               short userCounter = 0;
//               for (DataSnapshot ds: snapshot.getChildren()){
//                   if (userName.equals(ds.getKey())){
//                       if (pass.equals(ds.child("Account").child("Password").getValue())){
////                           returnUser = ds.getValue(User.class);
//                           returnUser.UserName = ds.child("Account").child("UserName").getValue().toString();
//                           returnUser.Password = ds.child("Account").child("Password").getValue().toString();
//                           returnUser.Name = ds.child("Account").child("Name").getValue().toString();
//                           returnUser.SurName = ds.child("Account").child("SurName").getValue().toString();
//                           returnUser.Mail = ds.child("Account").child("Mail").getValue().toString();
//                           loginResult = "ok";
//                       }
//                       else
//                           loginResult = "wrongpass";
//                   }
//                   else
//                       userCounter++;
//                   if (userCounter == snapshot.getChildrenCount())
//                       loginResult = "wronguser";
//               }
//               firebaseCallback.onUserDBCallback(loginResult);
//           }
//
//           @Override
//           public void onCancelled(@NonNull DatabaseError error) {
//
//           }
//       };
//       usersref.addValueEventListener(valueEventListener);
//    }


}


