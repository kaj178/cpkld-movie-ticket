package com.cpkld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.cpkld.model.entity.Menu;
import com.cpkld.model.entity.Seat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Integer bookingId;
    private Integer amountItem; // Number of ticket
    private LocalDateTime bookingTime;
    private LocalDateTime startTime;
    private Integer showTimeId;
    private List<Seat> seats;
    private Integer status;
    private double totalPrice;
    private String promotionName;
    private Integer customerId;
    private List<Menu> menus;
}
