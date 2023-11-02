package com.cpkld.service;

import java.util.List;

import com.cpkld.model.dto.UserDTO;
import com.cpkld.model.entity.User;

public interface AuthService {
    public void saveAccount(UserDTO accountDTO);

    public User findByEmail(String email);

    public List<UserDTO> getAllAccounts();
}
