package com.cpkld.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpkld.service.LanguageService;

@RestController
@RequestMapping("/api/v1/language")
public class LanguageAPI {
    @Autowired
    private LanguageService language;

    @GetMapping
    public ResponseEntity<?> readAllStudio() {
        return language.getAll();
    }
}
