package com.example.lab_8_3.models;

import androidx.annotation.NonNull;

public class Movie {
    private int id;
    private String name;
    private int year;
    private int rating;
    private String review;

    public Movie(String name, int year, int rating, String review) {
        this.name = name;
        this.year = year;
        this.rating = rating;
        this.review = review;
    }

    public Movie(int id, String name, int year, int rating, String review) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.rating = rating;
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @NonNull
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                '}';
    }
}
