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
        List<Ticket> tickets = ticketRepository.findAll();
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        tickets.stream().map(this::convertEntityToDTO).collect(Collectors.toList())
                ),
                HttpStatus.OK
        );
    }

    private TicketDTO convertEntityToDTO(Ticket ticket) {
        List<Seat> seats = ticket.getSeats();
        List<MenuBooking> menuBookings = ticket.getBooking().getMenuBookings();
        StringBuilder combos = new StringBuilder();
        StringBuilder strSeats = new StringBuilder();


        for (Seat item : seats) {
            strSeats.append(item.getSeatName()).append(", ");
        }

        for (MenuBooking item : menuBookings) {
            combos.append(item.getMenu().getName()).append(", ");
        }

        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setTicketId(ticket.getTicketId());
        ticketDTO.setStartTime(ticket.getShowTime().getStartTime());
        ticketDTO.setMovieName(ticket.getShowTime().getMovie().getName());
        ticketDTO.setType("nội dung");
        ticketDTO.setPerson("trên 18 tuổi");
        ticketDTO.setCombo(combos.toString());
        ticketDTO.setSeats(strSeats.toString());
        ticketDTO.setTotalPrice(ticket.getBooking().getTotalPrice());
        return ticketDTO;
    }
}
