package com.cpkld.api.controller;

import com.cpkld.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieApi {
    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<?> getAllmovies() {
        return movieService.getAll();
    }

    @GetMapping(params = "page")
    public ResponseEntity<?> getAllMovies(@RequestParam("page") int page) {
        return movieService.getAll(page - 1);
    }
}
