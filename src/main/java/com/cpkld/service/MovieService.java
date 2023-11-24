package com.cpkld.service;

import org.springframework.http.ResponseEntity;

public interface MovieService {

    //Tra ve tat danh sach phim
    ResponseEntity<?> getAll();

    // Phan trang
    ResponseEntity<?> getAll(int page);

    //Tra ve danh sach Movie dang chieu
    ResponseEntity<?> getListPlayingMovies();

    //Tra ve danh sach Movie sap chieu
    ResponseEntity<?> getListUpcomingMovies();

    //Tra ve danh sach Movie dang chieu theo the loai
    ResponseEntity<?> getListPlayingMoviesById(Integer genreMovie);

    //Tra ve danh sach Movie sap chieu
    ResponseEntity<?> getListUpcomingMoviesById(Integer genreMovie);
}
