package com.cpkld.dto;

import com.cpkld.model.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
