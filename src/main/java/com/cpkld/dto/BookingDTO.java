package com.cpkld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Integer bookingId;
//    private List<Integer> ticketsId = new ArrayList<>();
    private String email;
    private LocalDateTime startTime;
//    private String movieName;
    private String format;

//    private String theaterName;
    private Integer customerId;
    private String combo;
    private Integer amountItem;
    private int status;
    private double totalPrice;
    private String promotionName;
}
