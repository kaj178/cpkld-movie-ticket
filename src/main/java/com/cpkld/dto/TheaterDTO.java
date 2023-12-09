package com.cpkld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheaterDTO {
    private Integer theaterId;
    private String theaterName;
    private String address;
    private String phoneNumber;
    private int amountRoom;
}
