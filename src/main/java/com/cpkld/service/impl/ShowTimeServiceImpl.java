package com.cpkld.service.impl;

import com.cpkld.dto.ShowTimeDTO;
import com.cpkld.model.entity.*;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.*;
import com.cpkld.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowTimeServiceImpl implements ShowTimeService {
    @Autowired
    private ShowTimeRepository showTimeRepository;
    public ResponseEntity<?> getAll() {
        List<ShowTime> showTimes = showTimeRepository.findAll();
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        showTimes.stream().map(this::convertEntityToDTO)
                                .collect(Collectors.toList())
                ),
                HttpStatus.OK
        );
    }



    public ShowTimeDTO convertEntityToDTO(ShowTime showTime) {
        ShowTimeDTO showTimeDTO = new ShowTimeDTO();
//        List<Ticket> tickets = new ArrayList<>();


        showTimeDTO.setShowTimeId(showTime.getId());
        showTimeDTO.setStartTime(showTime.getStartTime());
        showTimeDTO.setEndTime(showTime.getEndTime());
        showTimeDTO.setStatus(showTime.getStatus());

        Movie movie = showTime.getMovie();
        showTimeDTO.setMovieId(movie.getMovieId());

        Format format = showTime.getFormat();
        showTimeDTO.setFormatName(format.getName());

        Room room = showTime.getRoom();
        Theater theater = room.getTheater();
        showTimeDTO.setTheaterName(theater.getName());

        return showTimeDTO;
    }
}
