package com.cpkld.service.impl;

import com.cpkld.dto.MovieDTO;
import com.cpkld.model.entity.Movie;
import com.cpkld.model.entity.MovieGenre;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.MovieRepository;
import com.cpkld.service.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public ResponseEntity<?> getAll() {
        List<Movie> movies = movieRepository.findAll();
        return new ResponseEntity<>(
            new ApiResponse<>(
                HttpStatus.OK.value(), 
                "Success", 
                movies.stream().map(this::convertEntityToDTO).collect(Collectors.toList())
            ),
            HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> getAll(int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("movie_id").ascending());
        Page<Movie> movies = movieRepository.findAllMovies(pageable);
        // for (Movie movie : movies) {
        //     System.out.println(movie.toString());
        // }
        return new ResponseEntity<>(
            new ApiResponse<>(
                HttpStatus.OK.value(), 
                "Success", 
                movies.stream().map(this::convertEntityToDTO).collect(Collectors.toList())),
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
    public ResponseEntity<?> getListPlayingMoviesById(Integer genreMovie) {
        return null;
    }

    @Override
    public ResponseEntity<?> getListUpcomingMoviesById(Integer genreMovie) {
        return null;
    }

    private MovieDTO convertEntityToDTO(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        List<String> movieGenreName = new ArrayList<>();
        for (MovieGenre genre : movie.getMovieGenres()) {
            movieGenreName.add(genre.getName());
        }
        movieDTO.setMovieId(movie.getMovieId());
        movieDTO.setName(movie.getName());
        movieDTO.setStudio(movie.getStudio());
        movieDTO.setMovieGenres(movieGenreName);
        movieDTO.setYear(movie.getYear());
        movieDTO.setTime(movie.getTime());
        movieDTO.setLanguage(movie.getLanguage().getName());
        movieDTO.setDirector(movie.getDirector());
        movieDTO.setRating(5);
        movieDTO.setStory(movie.getStory());
        movieDTO.setPremiere(movie.getPremiere());
        movieDTO.setUrlTrailer(movie.getUrlTrailer());
        movieDTO.setAge(movie.getAge());
        return movieDTO;
    }
}
