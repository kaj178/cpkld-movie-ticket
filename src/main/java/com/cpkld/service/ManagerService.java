package com.cpkld.service;

import org.springframework.http.ResponseEntity;

import com.cpkld.dto.ManagerDTO;
import com.cpkld.dto.UserDTO;

public interface ManagerService {
    ResponseEntity<?> getAll();

    ResponseEntity<?> getById(Integer id);

    ResponseEntity<?> getPaginated(int page);

    ResponseEntity<?> add(UserDTO userDTO);

    ResponseEntity<?> update(Integer id, ManagerDTO newManagerDTO);

    ResponseEntity<?> delete(Integer id);
}
