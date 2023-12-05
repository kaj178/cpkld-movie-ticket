package com.cpkld.service;

import org.springframework.http.ResponseEntity;

public interface TheaterService {
    ResponseEntity<?> getAllTheaters();
    ResponseEntity<?> getAllTheaters(int page);
}
