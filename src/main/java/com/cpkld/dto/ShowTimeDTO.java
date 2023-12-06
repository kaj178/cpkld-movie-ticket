package com.cpkld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowTimeDTO {
    private Integer showTimeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double price;
    private Integer movieId;
    private Integer roomId;
    private String formatName;
}
