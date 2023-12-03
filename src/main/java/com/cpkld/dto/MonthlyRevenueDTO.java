package com.cpkld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyRevenueDTO {
    private int year;
    private int quarterly;
    private int month;
    private int amountInvoice;
    private double totalRevenue;
}
