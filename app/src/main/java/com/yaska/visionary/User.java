package com.yaska.visionary;

import java.io.Serializable;

public class User implements Serializable {
    public String Name;
    public String SurName;
    public String Mail;
    public String UserName;
    public String Password;

    public User(String name, String surName, String mail, String userName, String password){

        this.Name = name;
        this.SurName = surName;
        this.Mail = mail;
        this.UserName = userName;
        this.Password = password;

    }

    public User() {

    }
}
