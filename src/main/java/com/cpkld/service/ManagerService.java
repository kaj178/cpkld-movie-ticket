package com.cpkld.service;

import org.springframework.http.ResponseEntity;

import com.cpkld.dto.ManagerDTO;

public interface ManagerService {
    ResponseEntity<?> getAll();

    ResponseEntity<?> getById(Integer id);

    ResponseEntity<?> getPaginated(int page);

    ResponseEntity<?> add(ManagerDTO managerDTO);

    ResponseEntity<?> update(Integer id, ManagerDTO newManagerDTO);

    ResponseEntity<?> delete(Integer id);
}
