package com.cpkld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private Integer ticketId;
    private LocalDateTime startTime;
    private String movieName;
    private String type;

    //Doi tuong la cai gi
    private String person;

    private String theaterName;

    private String combo;
    private String seats;

    private double totalPrice;

}
