package com.cpkld.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cpkld.dto.UserDTO;
import com.cpkld.model.entity.Role;
import com.cpkld.model.entity.User;
import com.cpkld.repository.RoleRepository;
import com.cpkld.repository.UserRepository;
import com.cpkld.service.AuthService;

public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveAccount(UserDTO accountDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAccount'");
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not existed");
        }
        return user;
    }

    @Override
    public List<UserDTO> getAllAccounts() {
        List<User> userList = userRepository.findAll();
        return null;
    }

    private UserDTO convertEntityToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        return null;
    }
    
    private Role saveRoleIfNotExisted(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        return roleRepository.save(role);
    }
}
