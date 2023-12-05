package com.cpkld.api.controller;

import com.cpkld.service.FormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/format")
public class FormatApi {
    @Autowired
    private FormatService formatService;

    @GetMapping
    public ResponseEntity<?> getAllFormat() {
        return formatService.getAllFormat();
    }

    @GetMapping("/{formatId}")
    public ResponseEntity<?> getFormatById(@PathVariable("formatId") Integer formatId) {
        return formatService.getFormatById(formatId);
    }
}
