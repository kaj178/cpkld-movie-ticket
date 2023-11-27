package com.cpkld.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpkld.service.GenreService;

@RestController
@RequestMapping("/api/v1/genre")
public class GenreApi {
    
    @Autowired
    private GenreService genreService;

    @GetMapping
    public ResponseEntity<?> readAllGenres() {
        return genreService.getAllGenres();
    }

}
