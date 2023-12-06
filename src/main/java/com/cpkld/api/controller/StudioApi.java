package com.cpkld.api.controller;

import com.cpkld.service.MovieService;
import com.cpkld.service.ShowTimeService;
import com.cpkld.service.StudioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/studio")
public class StudioApi {
    @Autowired
    private StudioService Studio;

    @GetMapping
    public ResponseEntity<?> readAllStudio() {
        return Studio.getAll();
    }
}
