package com.cpkld.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpkld.dto.UserDTO;
import com.cpkld.service.auth.AuthService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private AuthService userService;

    @GetMapping
    public List<UserDTO> readUsers() {
        return userService.getAllAccounts();
    }

}
