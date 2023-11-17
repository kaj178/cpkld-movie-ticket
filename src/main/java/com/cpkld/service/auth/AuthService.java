package com.cpkld.service.auth;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.cpkld.dto.UserDTO;
import com.cpkld.model.entity.User;

public interface AuthService extends UserDetailsService {
    public void saveCustomerAccount(UserDTO accountDTO);

    public void saveManagerAccount(UserDTO accountDTO);

    public User findUserByEmail(String email);

    public List<UserDTO> getAllAccounts();
}
