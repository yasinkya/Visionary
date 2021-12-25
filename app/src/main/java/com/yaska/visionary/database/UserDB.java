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

    public void check(String userName, String pass, final FirebaseCallback firebaseCallback){
       ValueEventListener valueEventListener = new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               short userCounter = 0;
               for (DataSnapshot ds: snapshot.getChildren()){
                   if (userName.equals(ds.getKey())){
                       if (pass.equals(ds.child("Account").child("Password").getValue()))
                           loginResult = "ok";
                       else
                           loginResult = ds.child("Account").child("Password").getValue().toString();
                   }
                   else
                       userCounter++;
                   if (userCounter == snapshot.getChildrenCount())
                       loginResult = String.valueOf(snapshot.getChildrenCount());

               }
               firebaseCallback.onCallback(loginResult);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       };
       usersref.addValueEventListener(valueEventListener);
    }

    public String check_paswd(String username, String passwd){

        usersref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                short userCounter = 0;
                loginResult = snapshot.getKey();


                for (DataSnapshot ds: snapshot.getChildren()){
                    loginResult = ds.getKey();
                }


//                for (DataSnapshot ds:snapshot.getChildren()){
//
//                    if(username.equals(ds.getKey().toString())){
//                        if (passwd.equals(ds.child(username).child("userInfo").child("Password").getValue().toString())){
//                            loginResult = "ok";
//                            return;
//                        }
//                        else{
//                            loginResult = "erpsw";
//                        }
//                    }
//                    else
//                        userCounter++;
//
//                    if (userCounter == ds.getChildrenCount())
//                        loginResult = String.valueOf(ds.getChildrenCount()) + "bulunamadı";
//                }
//
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loginResult = "noluyo";
            }

        });


        return loginResult;
    }


}
