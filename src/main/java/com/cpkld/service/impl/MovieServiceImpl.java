package com.cpkld.service.impl;

import com.cpkld.model.entity.Movie;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.MovieRepository;
import com.cpkld.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Override
    public ResponseEntity<?> getAll() {
        List<Movie> movies = movieRepository.findAll();
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.OK.value(), "Success", movies),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> getListPlayingMovies() {
        return null;
    }

    @Override
    public ResponseEntity<?> getListUpcomingMovies() {
        return null;
    }

    @Override
    public ResponseEntity<?> getListPlayingMoviesById(Integer GenreMovie) {
        return null;
    }

    @Override
    public ResponseEntity<?> getListUpcomingMoviesById(Integer GenreMovie) {
        return null;
    }
}
