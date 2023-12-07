package com.cpkld.api.controller;

import com.cpkld.dto.BookingDTO;
import com.cpkld.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingApi {
    @Autowired
    private BookingService bookingService;

    @GetMapping(params = "customer_id")
    public ResponseEntity<?> getBookingByID(@RequestParam("id") int id) {
        return bookingService.getBookingByCustomerID(id);
    }

    @GetMapping
    public ResponseEntity<?> readAllBooking() {
        return bookingService.getAll();
    }

    @GetMapping("/statistic")
    public ResponseEntity<?> getStatistic() {
        return bookingService.statisticBookings();
    }

    @PostMapping //(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> bookingDTO(@RequestBody BookingDTO bookingDTO) {
        return bookingService.add(bookingDTO);
    }
}
