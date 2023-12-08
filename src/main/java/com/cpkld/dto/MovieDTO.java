package com.cpkld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.cpkld.model.entity.Studio;

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

    private String studioId;

    private Studio studio;

    private String language;

    private List<String> movieGenres;
}
