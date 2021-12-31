package com.yaska.visionary.model;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable {
    public String Name;
    public String Genre;
    public String BannerImageUrl;
    public String Description;
    public String Time;
    public String VideId;
    public String ReleaseDate;
    public String Producer;
    public String Director;
    public String ScreenWriter;
    public List<Actor> Actors;

    public Movie(){

    }

    public Movie(String name, String genre, String bannerImageUrl, String  time, String producer, List<Actor> actors){
        this.Name = name;
        this.Genre = genre;
        this.BannerImageUrl = bannerImageUrl;
        this.Time = time;
        this.Producer = producer;
        this.Actors = actors;
    }

}
