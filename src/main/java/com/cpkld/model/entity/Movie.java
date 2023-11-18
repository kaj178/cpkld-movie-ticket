package com.cpkld.model.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Movie", schema = "public")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "movie_name")
    private String name;

    @Column(name = "premier")
    private Date premier;

    @Column(name = "time")
    private Integer time;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "trailer_url")
    private String trailerUrl;

    @Column(name = "age")
    private Float rating;

    @Column(name = "studio_id")
    private Integer studioId;

    @Column(name = "story")
    private Integer story;

    @ManyToOne
    @JoinColumn(name = "studio_id", insertable = false, updatable = false)
    private Studio studio;

}
