package com.example.filmonerim.model;

public class User {
    public String UserName;
    public String Password;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)

    }
    public User(String userName,String password){
        this.UserName=userName;
        this.Password=password;
    }

}
