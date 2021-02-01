package com.example.filmonerim.model;

public class Banners {
    Integer movieId;
    String movieName;
    String movieImgUrl;
    String movieFileUrl;

    public Banners(Integer movieId, String movieName, String movieImgUrl, String movieFileUrl) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieImgUrl = movieImgUrl;
        this.movieFileUrl = movieFileUrl;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieImgUrl() {
        return movieImgUrl;
    }

    public void setMovieImgUrl(String movieImgUrl) {
        this.movieImgUrl = movieImgUrl;
    }

    public String getMovieFileUrl() {
        return movieFileUrl;
    }

    public void setMovieFileUrl(String movieFileUrl) {
        this.movieFileUrl = movieFileUrl;
    }
}
