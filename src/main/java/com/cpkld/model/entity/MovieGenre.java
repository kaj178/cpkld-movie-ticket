package com.cpkld.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie_genre", schema = "public")
public class MovieGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Integer genreId;

    @Column(name = "genre_name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
        name = "detail_movie_genre", 
        joinColumns = @JoinColumn(name = "genre_id", insertable = false, updatable = false), 
        inverseJoinColumns = @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    )
    @JsonIgnoreProperties(value = "movie-moviegnres")
    private List<Movie> movies;
}