package com.cpkld.service.impl;

import com.cpkld.dto.TicketDTO;
import com.cpkld.model.entity.*;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.TicketRepository;
import com.cpkld.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public ResponseEntity<?> getAll() {
        return null;
//        List<Ticket> tickets = ticketRepository.findAll();
//        return new ResponseEntity<>(
//                new ApiResponse<>(
//                        HttpStatus.OK.value(),
//                        "Success",
//                        tickets.stream().map(this::convertEntityToDTO).collect(Collectors.toList())
//                ),
//                HttpStatus.OK
//        );
    }


}
