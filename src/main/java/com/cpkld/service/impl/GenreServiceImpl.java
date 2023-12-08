package com.cpkld.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cpkld.dto.GenreDTO;
import com.cpkld.model.entity.MovieGenre;
import com.cpkld.model.exception.notfound.GenreNotFoundException;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.GenreRepository;
import com.cpkld.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Override
    public ResponseEntity<?> getAllGenres() {
        List<MovieGenre> genres = genreRepository.findAll();
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(), "Success",
                        genres.stream()
                                .map(this::convertEntityToDTO)
                                .collect(Collectors.toList())),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getGenreById(Integer id) {
        Optional<MovieGenre> optional = genreRepository.findById(id);
        if (!optional.isPresent()) {
            throw new GenreNotFoundException("Genre not found");
        }
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        optional.stream().map(this::convertEntityToDTO).collect(Collectors.toList())),
                HttpStatus.OK);
    }

    @Override
    public MovieGenre getGenreByIdByAdmin(Integer id) {
        Optional<MovieGenre> optional = genreRepository.findById(id);
        return optional.get();
    }

    private GenreDTO convertEntityToDTO(MovieGenre genre) {
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(genre.getGenreId());
        genreDTO.setName(genre.getName());
        genreDTO.setDescription(genre.getDescription());
        return genreDTO;
    }
}
