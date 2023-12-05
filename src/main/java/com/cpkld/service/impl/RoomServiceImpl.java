package com.cpkld.service.impl;

import com.cpkld.dto.RoomDTO;
import com.cpkld.model.entity.Room;
import com.cpkld.model.exception.notfound.RoomNotFoundException;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.RoomRepository;
import com.cpkld.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomRepository roomRepository;

    @Override
    public ResponseEntity<?> getAllRooms() {
        return new ResponseEntity<>(
            new ApiResponse<>(
                HttpStatus.OK.value(), 
                "Success", 
                roomRepository.findAll().stream()
                    .map(this::convertEntityToDTO)
                    .collect(Collectors.toList())
            ),
            HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> getRoomById(Integer roomId) {
        Optional<Room> optional = roomRepository.getRoomByRoomId(roomId);
        if (optional.isEmpty()) {
            throw new RoomNotFoundException("Room not found!");
        }
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        optional.stream().map(this::convertEntityToDTO).collect(Collectors.toList())
                ),
                HttpStatus.OK
        );
    }

    private RoomDTO convertEntityToDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomID(room.roomId);
        roomDTO.setRoomName(room.roomName);
        roomDTO.setAmountSeats(room.amountSeats);
        return roomDTO;
    }
}
