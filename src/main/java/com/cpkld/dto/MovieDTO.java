package com.cpkld.dto;

import com.cpkld.model.entity.Studio;
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
    private LocalTime time;
    private int year;
    private int age;
    private String story;
    private int rating;
    private Studio studio;
    private String language;
    private List<String> movieGenres; 
}
