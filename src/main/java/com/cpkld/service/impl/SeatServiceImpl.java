package com.cpkld.service.impl;

import com.cpkld.dto.SeatDTO;
import com.cpkld.model.entity.*;
import com.cpkld.model.exception.notfound.MovieNotFoundException;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.SeatRepository;
import com.cpkld.service.SeatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    private SeatRepository seatRepository;

    public SeatDTO convertEntityToDTO(Seat seat) {
        SeatDTO seatDTO = new SeatDTO();
        seatDTO.setSeatId(seat.getSeatId());
        seatDTO.setSeatName(seat.getSeatName());
        seatDTO.setTypeId(seat.getType());
        return seatDTO;
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<Seat> seats = seatRepository.findAll();
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        seats.stream().map(this::convertEntityToDTO)
                                .collect(Collectors.toList())),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getSeatById(Integer id) {
        Optional<List<Seat>> optional = seatRepository.getSeatByID(id);
        if (!optional.isPresent()) {
            throw new MovieNotFoundException("Seat not found!");
        }
        List<Seat> seats = optional.get();
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        seats.stream().map(this::convertEntityToDTO).collect(Collectors.toList())),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getSeatByRoomId(Integer id) {
        Optional<List<Seat>> optional = seatRepository.getSeatByRoomID(id);
        if (!optional.isPresent()) {
            throw new MovieNotFoundException("Seat not found!");
        }
        List<Seat> seats = optional.get();
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        seats.stream().map(this::convertEntityToDTO).collect(Collectors.toList())),
                HttpStatus.OK);
    }
}
