package com.cpkld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudioDTO {
    public int id;
    public String address;
    public String name;
    public String email;
    public String phoneNumber;
}
