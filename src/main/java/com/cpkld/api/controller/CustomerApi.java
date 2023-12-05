package com.cpkld.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpkld.dto.CustomerDTO;
import com.cpkld.model.entity.Customer;
import com.cpkld.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerApi {
    
    @Autowired
    private CustomerService service;

    /* TEST /api/v1/customer */
    @GetMapping
    public ResponseEntity<?> readCustomers() {
        return service.getAll();
    }   

    /* /api/v1/customer/customer001 */
    @GetMapping("/{id}")
    public ResponseEntity<?> readCustomer(@PathVariable Integer id) {
        return service.getById(id);
    }

    /* /api/v1/customer?page={number} */
    @GetMapping(params = "page")
    public ResponseEntity<?> readCustomersPaginated(@RequestParam("page") int page) {
        return service.getPaginated(page - 1);
    }

    @GetMapping(params = "email")
    public ResponseEntity<?> readCustomerByEmail(@RequestParam("email") String email) {
        return service.getByEmail(email);
    }

    /* /api/v1/customer */
    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDTO customerDTO) {
        return service.add(customerDTO);
    }

    /* /api/v1/customer/customer010 */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(
        @PathVariable Integer id, 
        @RequestBody Customer customer
    ) {
        return service.update(id, customer);
    }

    /* /api/v1/customer/customer010  */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id) {
        return service.delete(id);
    }

}
