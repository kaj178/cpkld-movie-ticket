package com.cpkld.api.controller;

import com.cpkld.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping("/{date}")
    public ResponseEntity<?> getAllShowTimeByDate(@PathVariable(name = "date") Integer date) {
        return service.getAllShowTimeByDate(date);
    }
}
