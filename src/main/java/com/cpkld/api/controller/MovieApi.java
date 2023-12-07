package com.cpkld.api.controller;

import com.cpkld.dto.MovieDTO;
import com.cpkld.service.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieApi {
    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<?> readAllmovies() {
        return movieService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readMovieById(@PathVariable Integer id) {
        return movieService.getMovieById(id);
    }

    @GetMapping(params = "page")
    public ResponseEntity<?> readAllMovies(@RequestParam("page") int page) {
        return movieService.getAll(page - 1);
    }

    @GetMapping("/hot")
    public ResponseEntity<?> readHotMovies() {
        return movieService.getHotMovies();
    }

    @GetMapping(value = "/hot", params = "page")
    public ResponseEntity<?> readHotMoviesPaginated(@RequestParam("page") int page) {
        return movieService.getHotMoviesPaginated(page - 1);
    }

    @GetMapping("/playing")
    public ResponseEntity<?> readAllPLayingMovies() {
        return movieService.getListPlayingMovies();
    }

    @GetMapping(value = "/playing", params = "genre-id")
    public ResponseEntity<?> readPlayingMoviesByGenreId(@RequestParam("genre-id") Integer id) {
        return movieService.getPlayingMoviesByGenreId(id);
    }

    @GetMapping("up-coming")
    public ResponseEntity<?> readAllUpcomingMovies() {
        return movieService.getListUpcomingMovies();
    }

    @PostMapping
    public ResponseEntity<?> createMovie(@RequestBody MovieDTO movieDTO) {
        return movieService.add(movieDTO);
    }
}
