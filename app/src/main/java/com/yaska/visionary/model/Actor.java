package com.yaska.visionary.model;

import java.io.Serializable;

public class Actor implements Serializable {
    public String Name;
    public String ImageUrl;

    public Actor(String name, String imageUrl){
        this.Name = name;
        this.ImageUrl = imageUrl;
    }

    public Actor() {
    }
}
