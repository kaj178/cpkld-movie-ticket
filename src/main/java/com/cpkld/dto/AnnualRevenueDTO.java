package com.cpkld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnualRevenueDTO {
    private int year;
    private int amountInvoice;
    private double totalRevenue;
    private List<QuarterlyRevenueDTO> quarterlyRevenueDTOS = new ArrayList<>();
}
