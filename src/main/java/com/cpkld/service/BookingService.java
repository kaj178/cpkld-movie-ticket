package com.cpkld.service;

import org.springframework.http.ResponseEntity;

public interface BookingService {
    ResponseEntity<?> getAll();
    ResponseEntity<?> statisticBookings();
}
