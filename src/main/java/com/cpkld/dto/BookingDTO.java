package com.cpkld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Integer bookingId;
    private List<Integer> ticketsId;
    private String email;
    private LocalDateTime startTime;
    private String movieName;
    private String format;

    //Doi tuong = movie.age
    private int age;

    private String theaterName;

    private String combo;
    private String seats;

    private double totalPrice;
}
