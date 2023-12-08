package com.cpkld.service;

import org.springframework.http.ResponseEntity;
import com.cpkld.dto.ShowTimeDTO;

public interface ShowTimeService {
    ResponseEntity<?> getAll();

    ResponseEntity<?> getShowTimeById(Integer showTimeId);

    ResponseEntity<?> getAllShowTimeByDate(int YYYYMMDD);

    ResponseEntity<?> getShowTimeByDateAndGenre(int YYYYMMDD, Integer genreId);

    ResponseEntity<?> getShowTimeByMovieAndTheater(Integer movieId, Integer theaterId);

    ResponseEntity<?> getShowTimeByDateAndTheater(int YYYYMMDD, Integer theaterId);

    ResponseEntity<?> add(ShowTimeDTO movie);

    ResponseEntity<?> put(Integer showtimeID, ShowTimeDTO payload);
}
