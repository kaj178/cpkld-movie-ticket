package com.cpkld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Integer bookingId;
    private Integer amountItem; // NUmber of ticket
    private LocalDateTime bookingTime;
    // private List<Integer> ticketsId = new ArrayList<>();
    // private String email;
    private LocalDateTime startTime;
    private Integer showTimeId;
    // private String movieName;
    // private String format;
    // private String combo;
    // private String theaterName;
    private int status;
    private double totalPrice;
    private String promotionName;
    private Integer customerId;
}
