package com.cpkld.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.cpkld.model.entity.Movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private Integer ticketId;
    private Integer showTimeID;
    private Integer statusId;
    private Integer bookingId;
    private LocalTime startTime;
    private LocalDate dateTime;
    private Movie movie;
    private String type;
    private String customerEmail;
    private int age;
    private String theaterName;
    private String combo;
    private Integer seatsId;
    private double totalPrice;
}
