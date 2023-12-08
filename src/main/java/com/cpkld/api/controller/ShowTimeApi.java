package com.cpkld.api.controller;

import com.cpkld.dto.ShowTimeDTO;
import com.cpkld.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/showtime")
public class ShowTimeApi {
    @Autowired
    private ShowTimeService service;

    @GetMapping
    public ResponseEntity<?> readShowTimes() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getShowTimeById(@PathVariable(name = "id") Integer showTimeId) {
        return service.getShowTimeById(showTimeId);
    }

    @GetMapping(value = "/date", params = "date")
    public ResponseEntity<?> getAllShowTimeByDate(@RequestParam("date") Integer date) {
        return service.getAllShowTimeByDate(date);
    }

    @GetMapping(value = "/genre", params = { "date", "genre" })
    public ResponseEntity<?> getShowTimeByDateAndGenre(@RequestParam("date") int date,
            @RequestParam("genre") Integer genreId) {
        return service.getShowTimeByDateAndGenre(date, genreId);
    }

    @GetMapping(value = "/theater", params = { "date", "theater" })
    public ResponseEntity<?> getShowTimeByDateAndTheater(@RequestParam("date") int date,
            @RequestParam("theater") Integer theaterId) {
        return service.getShowTimeByDateAndGenre(date, theaterId);
    }

    @GetMapping(value = "/movie_theater", params = { "movie", "theater" })
    public ResponseEntity<?> getShowTimeByMovieAndTheater(@RequestParam("movie") Integer movieId,
            @RequestParam("theater") Integer theaterId) {
        return service.getShowTimeByMovieAndTheater(theaterId, movieId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createShowTime(@RequestBody ShowTimeDTO showtime) {
        return service.add(showtime);
    }

    @PutMapping("/{showtimeID}")
    public ResponseEntity<?> UpdateShowTime(@PathVariable("showtimeID") Integer showtimeId,
            @RequestBody ShowTimeDTO showtime) {
        return service.put(showtimeId, showtime);
    }
}
