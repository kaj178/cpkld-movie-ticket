package com.cpkld.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Movie", schema = "public")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movie_id;

    @Column(name = "movie_name")
    private String name;

    @Column(name = "premier")
    private LocalDate premier;

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

    @Column(name = "studio_id")
    private Integer studioId;

    @Column(name = "story")
    private String story;

    @ManyToOne
    @JoinColumn(name = "studio_id", insertable = false, updatable = false)
    private Studio studio;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "detail_movie_genre", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))

    private Collection<MovieGenre> movieGenres;

    public Movie(String name, LocalDate premier, LocalTime time, String description,
                 String imageUrl, String trailerUrl, String story, Studio studio) {
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
