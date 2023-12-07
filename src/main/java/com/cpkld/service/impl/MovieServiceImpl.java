package com.cpkld.service.impl;

import com.cpkld.dto.MovieDTO;
import com.cpkld.model.entity.Movie;
import com.cpkld.model.entity.MovieGenre;
import com.cpkld.model.exception.existed.MovieExistedException;
import com.cpkld.model.exception.notfound.MovieNotFoundException;
import com.cpkld.model.response.ApiResponse;
// import com.cpkld.repository.DetailMovieGenre;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
                movies.stream().map(this::convertEntityToDTO).collect(Collectors.toList())),
            HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAll(int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("movie_id").ascending());
        Page<Movie> movies = movieRepository.findAllMovies(pageable);
        // for (Movie movie : movies) {
        // System.out.println(movie.toString());
        // }
        return new ResponseEntity<>(
            new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                movies.stream().map(this::convertEntityToDTO).collect(Collectors.toList())),
            HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getMovieById(Integer id) {
        Optional<Movie> optional = movieRepository.findById(id);
        if (!optional.isPresent()) {
            throw new MovieNotFoundException("Movie not found!");
        }
        return new ResponseEntity<>(
            new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                optional.stream().map(this::convertEntityToDTO).collect(Collectors.toList())),
            HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getHotMovies() {
        List<Movie> movies = movieRepository.findAll();
        return new ResponseEntity<>(
            new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                movies.stream()
                    .map(this::convertEntityToDTO)
                    .filter(movie -> movie.getRating() == 5 || movie.getRating() == 4)
                    .collect(Collectors.toList())),
            HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getHotMoviesPaginated(int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("movie_id").ascending());
        Page<Movie> movies = movieRepository.findAllMovies(pageable);
        return new ResponseEntity<>(
            new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                movies.stream()
                    .map(this::convertEntityToDTO)
                    .filter(movie -> movie.getRating() == 5 || movie.getRating() == 4)
                    .collect(Collectors.toList())),
            HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getListPlayingMovies() {
        List<Movie> movies = movieRepository.findAll();
        return new ResponseEntity<>(
            new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                movies.stream()
                    .map(this::convertEntityToDTO)
                    .filter(movie -> movie.getYear() == 2023)
                    .collect(Collectors.toList())),
            HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getPlayingMoviesByGenreId(Integer id) {
        List<Movie> movies = movieRepository.findPremiereMoviesByGenreId(id);
        return new ResponseEntity<>(
            new ApiResponse<>(
                HttpStatus.OK.value(), "Success",
                movies.stream()
                    .map(this::convertEntityToDTO)
                    .collect(Collectors.toList())),
            HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getListUpcomingMovies() {
        List<Movie> movies = movieRepository.findAll();
        return new ResponseEntity<>(
            new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                movies.stream()
                    .map(this::convertEntityToDTO)
                    .filter(movie -> movie.getYear() == 2024)
                    .collect(Collectors.toList())),
            HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> add(@RequestBody MovieDTO movieDTO) {
        Movie movie = new Movie();

        movie.setName(movieDTO.getName());
        movie.setDirector(movieDTO.getDirector());
        movie.setYear(movieDTO.getYear());
        movie.setPremiere(movieDTO.getPremiere());

        // Chinh sua url
        movie.setUrlTrailer(movieDTO.getUrlTrailer());
        movie.setVerticalPoster(movieDTO.getVerticalPoster());
        movie.setHorizontalPoster(movieDTO.getHorizontalPoster());

        movie.setTime(movieDTO.getTime());
        movie.setAge(movieDTO.getAge());
        movie.setStory(movieDTO.getStory());
        movie.setRating(movieDTO.getRating());

        List<Movie> movies = movieRepository.findAll();
        Movie tmpMovie = movies.get(movies.size() - 1);

        List<String> lstMovieGenre = movieDTO.getMovieGenres();

        movieRepository.save(movie);

        return new ResponseEntity<>(
            new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                movies.stream().toList()),
            HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> update(Integer movieId, MovieDTO movieDTO) {

        return null;
    }

    @Override
    public ResponseEntity<?> delete(Integer movieId) {
        return null;
    }

    private MovieDTO convertEntityToDTO(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        List<String> movieGenreNames = new ArrayList<>();
        for (MovieGenre genre : movie.getMovieGenres()) {
            movieGenreNames.add(genre.getName());
        }
        movieDTO.setMovieId(movie.getMovieId());
        movieDTO.setName(movie.getName());
        movieDTO.setStudio(movie.getStudio());
        movieDTO.setMovieGenres(movieGenreNames);
        movieDTO.setYear(movie.getYear());
        movieDTO.setTime(movie.getTime());
        movieDTO.setLanguage(movie.getLanguage().getName());
        movieDTO.setDirector(movie.getDirector());
        // movieDTO.setRating(new Random().nextInt(1, 6));
        movieDTO.setRating(movie.getRating());
        movieDTO.setStory(movie.getStory());
        movieDTO.setPremiere(movie.getPremiere());
        movieDTO.setUrlTrailer(movie.getUrlTrailer());
        movieDTO.setVerticalPoster(movie.getVerticalPoster());
        movieDTO.setHorizontalPoster(movie.getHorizontalPoster());
        movieDTO.setAge(movie.getAge());
        return movieDTO;
    }
}
