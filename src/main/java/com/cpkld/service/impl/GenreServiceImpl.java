package com.cpkld.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cpkld.dto.GenreDTO;
import com.cpkld.model.entity.MovieGenre;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.GenreRepository;
import com.cpkld.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Override
    public ResponseEntity<?> getAllGenres() {
        return new ResponseEntity<>(
            new ApiResponse<>(
                HttpStatus.OK.value(), "Success", 
                genreRepository.findAll()
                    .stream()
                    .map(this::convertEntityToDTO)
                    .collect(Collectors.toList())
            ), HttpStatus.OK
        );
    }

    private GenreDTO convertEntityToDTO(MovieGenre genre) {
        GenreDTO genreDTO = new GenreDTO();
        List<MovieGenre> genres = genreRepository.findAll();
        for (MovieGenre temp : genres) {
            genreDTO.setId(temp.getGenreId());
            genreDTO.setName(temp.getName());
            genreDTO.setDescription(temp.getDescription());
        }
        return genreDTO;
    }
}
