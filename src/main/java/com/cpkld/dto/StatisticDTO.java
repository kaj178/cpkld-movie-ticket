package com.cpkld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDTO {
    List<AnnualRevenueDTO> annualRevenueDTOS;
    List<MonthlyRevenueDTO> monthlyRevenueDTOS;
    List<QuarterlyRevenueDTO> quarterlyRevenueDTOS;
}
