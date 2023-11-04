package com.cpkld.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDTO {
    private Integer id;
    
    @NotEmpty
    private String fullname;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String address;

    @NotEmpty
    private String phone;
}
