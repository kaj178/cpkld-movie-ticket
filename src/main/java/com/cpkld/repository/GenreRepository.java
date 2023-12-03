package com.cpkld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cpkld.model.entity.MovieGenre;

@Repository
public interface GenreRepository extends JpaRepository<MovieGenre, Integer> {

    @Query(value = "select g.* from public.movie_genre g " +
            "join public.detail_movie_genre dmg on g.genre_id = dmg.genre_id " +
            "join public.movie m on m.movie_id = dmg.movie_id " +
            "where m.movie_id = :movieId",
    nativeQuery = true)
    MovieGenre getMovieGenreByMovieId(@Param("movieId") Integer movieId);
}
