package com.cpkld.api.controller;

import com.cpkld.dto.BookingDTO;
import com.cpkld.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<?> getBookingByID(@RequestParam("customer_id") Integer id) {
        return bookingService.getBookingByCustomerID(id);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> readAllBooking() {
        return bookingService.getAll();
    }

    @GetMapping("/statistic")
    public ResponseEntity<?> getStatistic() {
        return bookingService.statisticBookings();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> bookingDTO(@RequestBody BookingDTO bookingDTO) {
        return bookingService.addBooking(bookingDTO);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeBooking(@RequestBody BookingDTO bookingDTO) {
        return bookingService.addBooking(bookingDTO);
    }
}
