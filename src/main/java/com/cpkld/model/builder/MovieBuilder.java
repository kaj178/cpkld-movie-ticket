package com.cpkld.model.builder;

import com.cpkld.model.entity.Movie;
import com.cpkld.model.entity.Studio;

import java.time.LocalDate;
import java.time.LocalTime;

public interface MovieBuilder {
    MovieBuilder setName(String name);
    MovieBuilder setPremier(LocalDate premier);
    MovieBuilder setTime(LocalTime time);
    MovieBuilder setDescription(String description);
    MovieBuilder setImgUrl(String urlImg);
    MovieBuilder setUrlTrailer(String urlTrailer);
    MovieBuilder setRating(Float rating);
    MovieBuilder setStory(String story);

    //Tu dong lay khoa ngoai tu studio thong qua   @ManyToOne
    MovieBuilder setStudio(Studio studio);

    Movie build();
}
