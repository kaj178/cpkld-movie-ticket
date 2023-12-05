package com.cpkld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO {
    private Integer itemId;
    private String name;
    private double price;
    private String imgUrl;
    private String status;
    // private String description;
}
