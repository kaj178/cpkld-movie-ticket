package com.cpkld.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpkld.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserApiController {
    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<?> readAllUsers() {
        return service.getAll();
    }
}
