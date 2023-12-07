package com.cpkld.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cpkld.dto.BookingDTO;
import com.cpkld.model.entity.Menu;
import com.cpkld.model.entity.Ticket;

public interface BookingService {
    // public class BookingTemp {
    // private int NumberOfTickets;
    // private String BookingTime;
    // private String Voucher;
    // private int customer;
    // private int ShowTimeID;
    // private double TotalPrice;
    // private List<Ticket> ListTicket;
    // private List<Menu> ListMenu;

    // }

    ResponseEntity<?> getAll();

    ResponseEntity<?> statisticBookings();

    ResponseEntity<?> getBookingByCustomerId(int id);
}
