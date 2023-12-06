package com.cpkld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

import com.cpkld.model.entity.Movie;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private Integer ticketId;
    private LocalTime startTime;
    private LocalDate dateTime;
    // private String movieName;
    private Movie movie;
    private String type;
    private String customerEmail;
    private int age;
    private String theaterName;
    private String combo;
    private String seats;
    private double totalPrice;
}
