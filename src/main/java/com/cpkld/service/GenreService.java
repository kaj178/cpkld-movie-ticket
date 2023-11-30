package com.cpkld.service;

import org.springframework.http.ResponseEntity;

public interface GenreService {
    ResponseEntity<?> getAllGenres();

    ResponseEntity<?> getGenreById(Integer id);
}
