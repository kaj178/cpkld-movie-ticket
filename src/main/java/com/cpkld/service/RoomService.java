package com.cpkld.service;

import org.springframework.http.ResponseEntity;

public interface RoomService {
    ResponseEntity<?> getAllRooms();
    ResponseEntity<?> getRoomById(Integer roomId);
}
