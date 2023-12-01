package com.cpkld.service.impl;

import com.cpkld.repository.TicketRepository;
import com.cpkld.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
