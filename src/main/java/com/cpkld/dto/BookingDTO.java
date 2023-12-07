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
    private Integer amountItem; // Number of ticket
    private LocalDateTime bookingTime;
    private LocalDateTime startTime;
    private Integer showTimeId;
    private Integer seatId;
    private Integer status;
    private double totalPrice;
    private String promotionName;
    private Integer customerId;
}
