package com.cpkld.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cpkld.model.entity.Customer;
import com.cpkld.model.exception.existed.CustomerExistedException;
import com.cpkld.model.exception.notfound.CustomerNotFoundException;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.CustomerRepository;
import com.cpkld.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository repo;

    // Get list of all data
    @Override
    public ResponseEntity<?> getAll() {
        List<Customer> customerList = repo.findAll();
        ApiResponse<Customer> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "Success", customerList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    // Get data by id
    @Override
    public ResponseEntity<?> getById(String id) {
        Optional<Customer> optional = repo.findById(id);
        if (!optional.isPresent()) {
            throw new CustomerNotFoundException("Customer not found");
        }
        return new ResponseEntity<>(
            new ApiResponse<>(HttpStatus.OK.value(), "Success", optional.stream().toList()),
            HttpStatus.OK
        );
    }

    // Get list of data paginated
    @Override
    public ResponseEntity<?> getPaginated(int page) {
        // New instance for paginating, 5 elements in 1 page, sorted by id 
        Pageable paging = PageRequest.of(page, 5, Sort.by("customerId").ascending());
        Page<Customer> customerList = repo.findAll(paging);
        return new ResponseEntity<>(
            new ApiResponse<>(HttpStatus.OK.value(), "Success", customerList.stream().toList()),
            HttpStatus.OK
        );
    }

    // Add new customer, if existed - throws exception
    @Override
    public ResponseEntity<?> add(Customer customer) {
        Optional<Customer> optional = repo.findById(customer.getCustomerId());
        if (optional.isPresent()) {
            throw new CustomerExistedException("Customer existed");
        } else {
            repo.save(customer);
        }
        return new ResponseEntity<>(
            new ApiResponse<>(HttpStatus.CREATED.value(), "Success", optional.stream().toList()),
            HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> update(String id, Customer newCustomer) {
        Optional<Customer> optional = repo.findById(id);
        if (!optional.isPresent()) {
            throw new CustomerNotFoundException("Customer not found");
        } else {
            Customer customer = new Customer();
            customer.setCustomerId(newCustomer.getCustomerId());
            customer.setCustomerFullname(newCustomer.getCustomerFullname());
            customer.setCustomerAddress(newCustomer.getCustomerAddress());
            customer.setCustomerEmail(newCustomer.getCustomerEmail());
            customer.setPhoneNumber(newCustomer.getPhoneNumber());
            customer.setAccountId(newCustomer.getAccountId());
            repo.save(customer);
        }
        return new ResponseEntity<>(
            new ApiResponse<>(HttpStatus.OK.value(), "Success", optional.stream().toList()),
            HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        Optional<Customer> optional = repo.findById(id);
        if (!optional.isPresent()) {
            throw new CustomerNotFoundException("Customer not found");
        } else {
            repo.deleteById(id);
        }
        return new ResponseEntity<>(
            new ApiResponse<>(HttpStatus.OK.value(), "Delete user successfully!", optional.stream().toList()), 
            HttpStatus.OK
        );
    }

}
