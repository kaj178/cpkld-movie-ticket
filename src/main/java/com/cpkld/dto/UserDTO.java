package com.cpkld.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    
    @NotEmpty
    private String lastname;

    @NotEmpty
    private String firstname;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    @NotEmpty
    private String address;

    @NotEmpty
    private String phone;

    @NotEmpty(message = "Password should not be empty")
    private String password;
}
