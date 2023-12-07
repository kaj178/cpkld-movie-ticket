package com.cpkld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

import com.cpkld.model.entity.Movie;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowTimeDTO {
    private Integer showTimeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double price;
    private Movie movie;
    private Integer roomId;
    private Integer formatId;
}
