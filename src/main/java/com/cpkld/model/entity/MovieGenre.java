package com.cpkld.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie_genre", schema = "public")
public class MovieGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer genre_id;

    @Column(name = "genre_name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "movieGenres")
    private Collection<Movie> movies;
}