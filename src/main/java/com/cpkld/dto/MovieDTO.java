package com.cpkld.dto;

import com.cpkld.model.entity.MovieGenre;
import com.cpkld.model.entity.Studio;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MovieDTO {

    private Integer movieId;

    private String name;

    private String director;

    private LocalDate premiere;

    private String urlTrailer;

    private String verticalPoster;

    private String horizontalPoster;

    private LocalTime time;

    private Integer year;

    private Integer age;

    private String story;

    private Integer rating;

    private Studio studio;

    private String language;

    private List<String> movieGenres;

    private Integer studioId;

}
