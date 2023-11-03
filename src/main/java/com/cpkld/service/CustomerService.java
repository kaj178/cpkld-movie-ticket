package com.cpkld.service;

import org.springframework.http.ResponseEntity;

import com.cpkld.dto.CustomerDTO;
import com.cpkld.model.entity.Customer;

public interface CustomerService {
    ResponseEntity<?> getAll();

    ResponseEntity<?> getById(Integer id);

    ResponseEntity<?> getPaginated(int page);

    ResponseEntity<?> add(CustomerDTO customerDTO);

    ResponseEntity<?> update(Integer id, Customer newCustomer);

    ResponseEntity<?> delete(Integer id);
} 