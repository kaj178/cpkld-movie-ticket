package com.cpkld.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cpkld.dto.CustomerDTO;
import com.cpkld.model.entity.Customer;
import com.cpkld.model.entity.User;
import com.cpkld.model.exception.existed.CustomerExistedException;
import com.cpkld.model.exception.notfound.CustomerNotFoundException;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.CustomerRepository;
import com.cpkld.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository repo;

    private CustomerDTO convertEntityToDto(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setFullname(customer.getFullName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPhone(customer.getPhoneNumber());
        customerDTO.setPassword(customer.getUser().getPassword());
        customerDTO.setAddress(customer.getAddress());
        return customerDTO;
    }

    // Get list of all data
    @Override
    public ResponseEntity<?> getAll() {
        List<CustomerDTO> customerList = repo.findAll()
            .stream()
            .map(this::convertEntityToDto)
            .collect(Collectors.toList());
        ApiResponse<CustomerDTO> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "Success", customerList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    // Get data by id
    @Override
    public ResponseEntity<?> getById(Integer id) {
        Optional<Customer> optional = repo.findById(id);
        if (!optional.isPresent()) {
            throw new CustomerNotFoundException("Customer not found");
        }
        return new ResponseEntity<>(
            new ApiResponse<>(
                HttpStatus.OK.value(), 
                "Success", 
                optional.stream().map(this::convertEntityToDto).toList()
            ),
            HttpStatus.OK
        );
    }

    // Get list of data paginated
    @Override
    public ResponseEntity<?> getPaginated(int page) {
        // New instance for paginating, 5 elements in 1 page, sorted by id 
        Pageable paging = PageRequest.of(page, 5, Sort.by("id").ascending());
        Page<Customer> customerList = repo.findAll(paging);
        return new ResponseEntity<>(
            new ApiResponse<>(
                HttpStatus.OK.value(), 
                "Success", 
                customerList.stream().map(this::convertEntityToDto).toList()
            ),
            HttpStatus.OK
        );
    }

    // Add new customer, if existed - throws exception
    @Override
    public ResponseEntity<?> add(CustomerDTO customerDTO) {
        Optional<Customer> optional = repo.findById(customerDTO.getId());
        if (optional.isPresent()) {
            throw new CustomerExistedException("Customer existed");}
        // } else {
        //     repo.save(new User(

        //     ));
        // }
        return new ResponseEntity<>(
            new ApiResponse<>(HttpStatus.CREATED.value(), "Success", optional.stream().toList()),
            HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> update(Integer id, Customer newCustomer) {
        Optional<Customer> optional = repo.findById(id);
        if (!optional.isPresent()) {
            throw new CustomerNotFoundException("Customer not found");
        } else {
            Customer customer = new Customer();
            customer.setId(newCustomer.getId());
            customer.setFullName(newCustomer.getFullName());
            customer.setAddress(newCustomer.getAddress());
            customer.setEmail(newCustomer.getEmail());
            customer.setPhoneNumber(newCustomer.getPhoneNumber());
            //customer.setAccountId(newCustomer.getAccountId());
            repo.save(customer);
        }
        return new ResponseEntity<>(
            new ApiResponse<>(HttpStatus.OK.value(), "Success", optional.stream().toList()),
            HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
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
