package com.cpkld.service;

import org.springframework.http.ResponseEntity;

import com.cpkld.model.entity.Account;

public interface AccountService {
    ResponseEntity<?> getAll();

    ResponseEntity<?> getById(String id);

    ResponseEntity<?> getPaginated(int page);

    ResponseEntity<?> update(String id, Account account);

    ResponseEntity<?> delete(String id);
}
