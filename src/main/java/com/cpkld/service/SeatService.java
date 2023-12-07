package com.cpkld.service;

import org.springframework.http.ResponseEntity;

public interface SeatService {
    ResponseEntity<?> getAll();

    ResponseEntity<?> getSeatByRoomId(Integer roomID);

    ResponseEntity<?> getSeatById(Integer seatID);
}
