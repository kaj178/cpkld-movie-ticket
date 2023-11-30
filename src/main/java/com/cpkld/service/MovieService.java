package com.cpkld.service;

import org.springframework.http.ResponseEntity;

public interface MovieService {

    ResponseEntity<?> getAll();

    ResponseEntity<?> getAll(int page);

    ResponseEntity<?> getMovieById(Integer id);

    ResponseEntity<?> getHotMovies();

    ResponseEntity<?> getHotMoviesPaginated(int page);

    ResponseEntity<?> getListPlayingMovies();

    ResponseEntity<?> getPlayingMoviesByGenreId(Integer id);

    ResponseEntity<?> getListUpcomingMovies();

}
