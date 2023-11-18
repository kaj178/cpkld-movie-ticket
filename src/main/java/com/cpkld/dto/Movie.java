package com.cpkld.dto;

import com.cpkld.model.entity.Studio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private Integer id;
    private String name;
    private Date premier;
    private LocalTime time;
    private String description;
    private String imgUrl;
    private String trailerUrl;
    private Float rating;
    private String story;
    private Studio studio;
}
