package com.cpkld.model.concretebuiler;

import com.cpkld.model.builder.MovieBuilder;
import com.cpkld.model.entity.Movie;
import com.cpkld.model.entity.Studio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class MovieConcreteBuilder implements MovieBuilder {
    private String name;
    private LocalDate premier;
    private LocalTime time;
    private String description;
    private String imgUrl;
    private String trailerUrl;
    private Float rating;
    private String story;
    private Studio studio;

    @Override
    public MovieBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public MovieBuilder setPremier(LocalDate premier) {
        this.premier = premier;
        return this;
    }


    @Override
    public MovieBuilder setTime(LocalTime time) {
        this.time = time;
        return this;
    }

    @Override
    public MovieBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public MovieBuilder setImgUrl(String urlImg) {
        this.imgUrl = urlImg;
        return this;
    }

    @Override
    public MovieBuilder setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
        return this;
    }

    @Override
    public MovieBuilder setRating(Float rating) {
        this.rating = rating;
        return this;
    }

    @Override
    public MovieBuilder setStory(String story) {
        this.story = story;
        return this;
    }

    @Override
    public MovieBuilder setStudio(Studio studio) {
        this.studio = studio;
        return this;
    }

    @Override
    public Movie build() {
        return new Movie(name, premier, time, description, imgUrl, trailerUrl, story, studio);
    }
}
