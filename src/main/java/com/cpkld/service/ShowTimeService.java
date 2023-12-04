package com.cpkld.service;

import org.springframework.http.ResponseEntity;

public interface ShowTimeService {
    public ResponseEntity<?> getAll();

    ResponseEntity<?> getShowTimeById(Integer showTimeId);

    ResponseEntity<?> getAllShowTimeByDate(int YYYYMMDD);

    ResponseEntity<?> getShowTimeByDateAndGenre(int YYYYMMDD, Integer genreId);

    ResponseEntity<?> getShowTimeByMovieAndTheater(Integer movieId, Integer theaterId);

    ResponseEntity<?> getShowTimeByDateAndTheater(int YYYYMMDD, Integer theaterId);


}
