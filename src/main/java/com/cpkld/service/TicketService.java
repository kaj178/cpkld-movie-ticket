package com.cpkld.service;

import org.springframework.http.ResponseEntity;

public interface TicketService {
    ResponseEntity<?> getAll();

    ResponseEntity<?> getAllTicketsByShowTimeId(Integer showtimeId);

    ResponseEntity<?> getAllTicketsByBookingId(Integer bookingId);
}
