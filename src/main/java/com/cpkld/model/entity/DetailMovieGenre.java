package com.cpkld.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detail_movie_genre", schema = "public")
public class DetailMovieGenre {
    @Id
    @Column(name = "movie_id")
    private Integer movieId;
    @Id
    @Column(name = "movie_genre_id")
    private Integer movieGenreId;

    @ManyToOne
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "movie_genre_id", insertable = false, updatable = false)
    private MovieGenre movieGenre;
}
