package com.cpkld.api.controller;

import com.cpkld.service.SeatService;
import com.cpkld.service.StudioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/seat")
public class SeatAPi {
    @Autowired

    private SeatService service;

    @GetMapping(params = "seat_id")
    public ResponseEntity<?> getseatByID(@RequestParam(name = "seat_id") Integer seatID) {
        return service.getSeatById(seatID);
    }

    @GetMapping(value = "/room", params = "room_id")
    public ResponseEntity<?> getseatByRoomID(@RequestParam(name = "room_id") Integer roomID) {
        return service.getSeatByRoomId(roomID);
    }
}
