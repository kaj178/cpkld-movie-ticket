package com.cpkld.api.controller;

import com.cpkld.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/room")
public class RoomApi {
    @Autowired
    RoomService roomService;

    @GetMapping
    public ResponseEntity<?> readAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readRoomById(@PathVariable("id") Integer roomId) {
        return roomService.getRoomById(roomId);
    }
}
