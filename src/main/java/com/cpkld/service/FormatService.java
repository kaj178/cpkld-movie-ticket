package com.cpkld.service;

import org.springframework.http.ResponseEntity;

public interface FormatService {
    ResponseEntity<?> getAllFormat();
    ResponseEntity<?> getFormatById(Integer formatId);
}
