package com.cpkld.service.impl;

import com.cpkld.dto.MovieDTO;
import com.cpkld.model.entity.Language;
import com.cpkld.model.entity.Movie;
import com.cpkld.model.entity.MovieGenre;
import com.cpkld.model.entity.Studio;
import com.cpkld.model.exception.notfound.MovieNotFoundException;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.LanguageRepository;
import com.cpkld.repository.MovieRepository;
import com.cpkld.repository.StudioRepository;
import com.cpkld.repository.GenreRepository;
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
    @Autowired
    private StudioRepository studioRepository;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Override
    public ResponseEntity<?> getAll() {
        List<Movie> movies = movieRepository.findAll();
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        movies.stream().map(this::convertEntityToDTO)
                                .collect(Collectors.toList())),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAll(int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("movie_id").ascending());
        Page<Movie> movies = movieRepository.findAllMovies(pageable);
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        movies.stream().map(this::convertEntityToDTO)
                                .collect(Collectors.toList())),
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
                        optional.stream().map(this::convertEntityToDTO)
                                .collect(Collectors.toList())),
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
                                .filter(movie -> movie.getRating() == 5
                                        || movie.getRating() == 4)
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
                                .filter(movie -> movie.getRating() == 5
                                        || movie.getRating() == 4)
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

        Studio studio1 = studioRepository.findById(Integer.parseInt(movieDTO.getStudioId())).get();
        Language language1 = languageRepository.findById(Integer.parseInt(movieDTO.getLanguage())).get();
        List<String> Genre = movieDTO.getMovieGenres();
        List<MovieGenre> Genretoadd = new ArrayList<MovieGenre>();
        for (String i : Genre) {
            MovieGenre genre1 = genreRepository.findById(Integer.parseInt(i)).get();
            Genretoadd.add(genre1);
        }
        movie.setHorizontalPoster(movieDTO.getHorizontalPoster());
        movie.setVerticalPoster(movieDTO.getVerticalPoster());
        movie.setName(movieDTO.getName());
        movie.setDirector(movieDTO.getDirector());
        movie.setYear(movieDTO.getYear());
        movie.setPremiere(movieDTO.getPremiere());
        movie.setUrlTrailer(movie.getUrlTrailer());
        movie.setTime(movieDTO.getTime());
        movie.setAge(movieDTO.getAge());
        movie.setStory(movieDTO.getStory());
        movie.setRating(5);
        movie.setStudio(studio1);
        movie.setLanguage(language1);
        movie.setMovieGenres(Genretoadd);
        movieRepository.saveMovie(
                movie.getName(),
                movie.getDirector(),
                movie.getYear(),
                movie.getPremiere(),
                movie.getUrlTrailer(),
                movie.getVerticalPoster(),
                movie.getHorizontalPoster(),
                movie.getTime(),
                movie.getAge(),
                movie.getStory(),
                movie.getRating(),
                movie.getStudio().getId(),
                movie.getLanguage().getId());
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        null),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> update(Integer movieId, @RequestBody MovieDTO movie) {
        Movie _movie = new Movie();
        Movie newMovie = movieRepository.findById(movieId).orElse(null);
        Studio studio1 = studioRepository.findById(Integer.parseInt(movie.getStudioId())).get();
        Language language1 = languageRepository.findById(Integer.parseInt(movie.getLanguage())).get();
        _movie.setMovieId(movieId);
        _movie.setHorizontalPoster(newMovie.getHorizontalPoster());
        _movie.setVerticalPoster(movie.getVerticalPoster());
        _movie.setName(movie.getName());
        _movie.setDirector(movie.getDirector());
        _movie.setYear(movie.getYear());
        _movie.setPremiere(movie.getPremiere());
        _movie.setUrlTrailer(movie.getUrlTrailer());
        _movie.setTime(movie.getTime());
        _movie.setAge(movie.getAge());
        _movie.setStory(movie.getStory());
        _movie.setRating(5);
        _movie.setStudio(studio1);
        _movie.setLanguage(language1);
        _movie.setMovieGenres(newMovie.getMovieGenres());
        Movie _movie2 = movieRepository.save(_movie);
        List<Movie> movies = new ArrayList<Movie>();
        movies.add(_movie2);
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        movies.stream().toList()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> delete(Integer movieId) {
        movieRepository.deleteById(movieId);
        List<Movie> resp = new ArrayList<>();
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        resp.stream().toList()),
                HttpStatus.OK);
    }

    private MovieDTO convertEntityToDTO(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        List<String> movieGenreNames = new ArrayList<>();
        for (MovieGenre genre : movie.getMovieGenres()) {
            movieGenreNames.add(genre.getName());
        }
        movieDTO.setMovieId(movie.getMovieId());
        movieDTO.setName(movie.getName());
        movieDTO.setStudioId(String.valueOf(movie.getStudio().getId()));
        movieDTO.setStudio(movie.getStudio());
        movieDTO.setMovieGenres(movieGenreNames);
        movieDTO.setYear(movie.getYear());
        movieDTO.setTime(movie.getTime());
        movieDTO.setLanguage(movie.getLanguage().getName());
        movieDTO.setDirector(movie.getDirector());
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
