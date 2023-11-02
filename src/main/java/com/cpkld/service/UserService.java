package com.cpkld.service;

import org.springframework.http.ResponseEntity;

import com.cpkld.model.entity.User;

public interface UserService {
    ResponseEntity<?> getAll();

    ResponseEntity<?> getById(Integer id);

    ResponseEntity<?> getPaginated(int page);

    ResponseEntity<?> update(Integer id, User account);

    ResponseEntity<?> delete(Integer id);
}
