package com.cpkld.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Movie", schema = "public")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "movie_name")
    private String name;

    @NotNull
    @Column(name = "premier")
    private LocalDate premier;

    @NotNull
    @Column(name = "time")
    private LocalTime time;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "trailer_url")
    private String trailerUrl;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "story")
    private String story;

    @Column(name = "studio_id")
    private Integer studioId;

    @ManyToOne
    @JoinColumn(name = "studio_id", insertable = false, updatable = false)
    private Studio studio;

    public Movie(String name, LocalDate premier, LocalTime time, String description, String
                 imageUrl, String trailerUrl, String story, Studio studio) {
        this.name = name;
        this.premier = premier;
        this.time = time;
        this.description = description;
        this.imageUrl = imageUrl;
        this.trailerUrl = trailerUrl;
        this.story = story;
        this.studio = studio;
    }
}
