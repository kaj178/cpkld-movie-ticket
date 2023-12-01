package com.cpkld.api.controller;

import com.cpkld.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingApi {
    @Autowired
    private BookingService bookingService;

    @GetMapping
    public ResponseEntity<?> readAllBooking() {
        return bookingService.getAll();
    }
}
