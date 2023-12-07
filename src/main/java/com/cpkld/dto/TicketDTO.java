package com.cpkld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

import java.time.LocalDate;
import java.time.LocalTime;

import com.cpkld.model.entity.Movie;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private Integer ticketId;
    private Integer showTimeID;
    private Integer status_id;
    private Integer booking_id;
    // private LocalTime startTime;
    // private LocalDate dateTime;
    // private String movieName;
    // private Movie movie;
    // private String type;
    // private String customerEmail;
    // private int age;
    // private String theaterName;
    // private String combo;
    private Integer seats_id;
    // private double totalPrice;
}
