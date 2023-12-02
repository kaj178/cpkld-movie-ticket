package com.cpkld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowTimeDTO {
    private Integer showTimeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private Integer movieId;
    private String formatName;
    private String theaterName;
//    private List<Ticket> tickets;
}
