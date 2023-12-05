package com.cpkld.service.impl;

import com.cpkld.dto.TheaterDTO;
import com.cpkld.model.entity.Theater;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.TheaterRepository;
import com.cpkld.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TheaterServiceImpl implements TheaterService {
    @Autowired
    private TheaterRepository theaterRepository;

    @Override
    public ResponseEntity<?> getAllTheaters() {
        return new ResponseEntity<>(
            new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                theaterRepository.findAll().stream().map(this::convertEntityToDTO).collect(Collectors.toList())
            ),
            HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> getAllTheaters(int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("theater_id").ascending());
        Page<Theater> theaterPage = theaterRepository.getAllTheater(pageable);
        return new ResponseEntity<>(
            new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                theaterPage.stream().map(this::convertEntityToDTO).collect(Collectors.toList())
            ),
            HttpStatus.OK
        );
    }

    private TheaterDTO convertEntityToDTO(Theater theater) {
        TheaterDTO theaterDTO = new TheaterDTO();
        theaterDTO.setTheaterId(theater.getId());
        theaterDTO.setTheaterName(theater.getName());
        theaterDTO.setAddress(theater.getAddress());
        theaterDTO.setPhoneNumber(theater.getPhoneNumber());
        theaterDTO.setAmountRoom(theater.getNumberOfRooms());
        return theaterDTO;
    }
}
