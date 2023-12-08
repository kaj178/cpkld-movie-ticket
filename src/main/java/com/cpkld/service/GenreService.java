package com.cpkld.service;

import org.springframework.http.ResponseEntity;

import com.cpkld.model.entity.MovieGenre;

public interface GenreService {
    ResponseEntity<?> getAllGenres();

    ResponseEntity<?> getGenreById(Integer id);

    MovieGenre getGenreByIdByAdmin(Integer id);
}
