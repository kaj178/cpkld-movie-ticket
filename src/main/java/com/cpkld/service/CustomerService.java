package com.cpkld.service;

import org.springframework.http.ResponseEntity;

import com.cpkld.model.entity.Customer;

public interface CustomerService {
    ResponseEntity<?> getAll();

    ResponseEntity<?> getById(String id);

    ResponseEntity<?> getPaginated(int page);

    ResponseEntity<?> add(Customer customer);

    ResponseEntity<?> update(String id, Customer newCustomer);

    ResponseEntity<?> delete(String id);
} 