package com.cpkld.api.controller;

import com.cpkld.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ticket_admin")
public class TicketApi {
    @Autowired
    public TicketService ticketService;

    @GetMapping
    public ResponseEntity<?> readTickets() {
        return ticketService.getAll();
    }
}
