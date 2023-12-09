package com.cpkld.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpkld.dto.UserDTO;
import com.cpkld.service.ManagerService;

@RestController
@RequestMapping("/api/v1/manager")
public class ManagerApi {
    
    @Autowired
    private ManagerService managerService;
    
    @GetMapping
    public ResponseEntity<?> readManagers() {
        return managerService.getAll();
    }

    @GetMapping(params = "page")
    public ResponseEntity<?> readManagersPaginated(@RequestParam("page") int page) {
        return managerService.getPaginated(page - 1);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readManager(@PathVariable int id) {
        return managerService.getById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createManager(@RequestBody UserDTO userDTO) {
        return managerService.add(userDTO);
    }

}
