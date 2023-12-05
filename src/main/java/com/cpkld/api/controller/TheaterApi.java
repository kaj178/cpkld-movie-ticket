package com.cpkld.api.controller;

import com.cpkld.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/theater")
public class TheaterApi {
    @Autowired
    private TheaterService theaterService;

    @GetMapping
    public ResponseEntity<?> readAllTheaters() {
        return theaterService.getAllTheaters();
    }

    @GetMapping(value = "theaters", params = {"num_page"})
    public ResponseEntity<?> getAllTheaters(@RequestParam("num_page") int numPage) {
        return  theaterService.getAllTheaters(numPage-1);
    }
}
