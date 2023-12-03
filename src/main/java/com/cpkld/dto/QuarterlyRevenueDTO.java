package com.cpkld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuarterlyRevenueDTO {
    private int year;
    private int quarter;
    private int amountInvoice;
    private double totalRevenue;
    private List<MonthlyRevenueDTO> monthlyRevenueDTOS = new ArrayList<>();
}
