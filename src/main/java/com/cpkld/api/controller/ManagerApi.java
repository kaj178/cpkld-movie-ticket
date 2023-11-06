package com.cpkld.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpkld.service.ManagerService;

@RestController
@RequestMapping("/api/v1/manager")
public class ManagerApi {
    
    @Autowired
    private ManagerService service;

    @GetMapping
    public ResponseEntity<?> readManagers() {
        return service.getAll();
    }

    @GetMapping(params = "page, size")
    public ResponseEntity<?> readManagersPaginated(@RequestParam int page) {
        return service.getPaginated(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readManager(@PathVariable int id) {
        return service.getById(id);
    }

}
