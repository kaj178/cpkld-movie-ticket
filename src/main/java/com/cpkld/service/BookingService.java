package com.cpkld.service;

import org.springframework.http.ResponseEntity;

import com.cpkld.dto.BookingDTO;

public interface BookingService {

    ResponseEntity<?> getAll();

    ResponseEntity<?> statisticBookings();

    ResponseEntity<?> getBookingByCustomerID(int id);

    // ResponseEntity<?> add(com.cpkld.api.controller.BookingApi.BookingTemp bookingDTO);
    ResponseEntity<?> addBooking(BookingDTO bookingDTO);

}
