package com.cpkld.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@Table(name = "movie", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Integer movieId;

    @Column(name = "movie_name")
    private String movieName;

    @Column(name = "director")
    private String director;

    @Column(name = "year")
    private int year;

    @Column(name = "premiere")
    private Date premiere;

    @Column(name = "url_trailer")
    private String urlTrailer;

    @Column(name = "time")
    private Time time;

    @Column(name = "age")
    private int age;

    @Column(name = "story")
    private String story;

}
