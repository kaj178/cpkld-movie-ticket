package com.cpkld.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpkld.service.AccountService;

@RestController
@RequestMapping("/api/v1/account")
public class AccountApiController {
    @Autowired
    private AccountService service;

    @GetMapping
    public ResponseEntity<?> readAllAccounts() {
        return service.getAll();
    }
}
