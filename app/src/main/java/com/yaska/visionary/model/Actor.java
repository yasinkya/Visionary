package com.yaska.visionary.model;

public class Actor {
    public String Name;
    public String ImageUrl;

    public Actor(String name, String imageUrl){
        this.Name = name;
        this.ImageUrl = imageUrl;
    }
    public Actor(String name){
        this.Name = name;
    }
    public Actor(){

    }
}
