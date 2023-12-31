package com.cpkld.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "movie", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id", updatable = false, columnDefinition = "serial")
    private Integer movieId;

    @Column(name = "movie_name")
    private String name;

    @Column(name = "director")
    private String director;

    @Column(name = "year")
    private Integer year;

    @Column(name = "premiere")
    private LocalDate premiere;

    @Column(name = "url_trailer")
    private String urlTrailer;

    @Column(name = "url_poster_vertical")
    private String verticalPoster;

    @Column(name = "url_poster_horizontal")
    private String horizontalPoster;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "age")
    private Integer age;

    @Column(name = "story")
    private String story;

    @Column(name = "rating")
    private int rating;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Studio.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "studio_id")
    @JsonIgnore
    @JsonBackReference(value = "studio-movie")
    private Studio studio;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Language.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "language_id")
    @JsonBackReference(value = "language-movie")
    private Language language;

    @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER, targetEntity = ShowTime.class)
    @JsonManagedReference(value = "showtime-movie")
    @JsonIgnore
    private List<ShowTime> showTimes;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "detail_movie_genre", joinColumns = @JoinColumn(name = "movie_id", insertable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "genre_id", insertable = false, updatable = false))
    @JsonIgnore
    @JsonIgnoreProperties(value = "movie-moviegnres")
    private List<MovieGenre> movieGenres;

}
