package com.cpkld.service;

import com.cpkld.model.entity.Movie;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface MovieService {

    //Tra ve tat danh sach phim
    ResponseEntity<?> getAll();

    //Tra ve danh sach Movie dang chieu
    ResponseEntity<?> getListPlayingMovies();

    //Tra ve danh sach Movie sap chieu
    ResponseEntity<?> getListUpcomingMovies();

    //Tra ve danh sach Movie dang chieu theo the loai
    ResponseEntity<?> getListPlayingMoviesById(Integer GenreMovie);

    //Tra ve danh sach Movie sap chieu
    ResponseEntity<?> getListUpcomingMoviesById(Integer GenreMovie);
}
