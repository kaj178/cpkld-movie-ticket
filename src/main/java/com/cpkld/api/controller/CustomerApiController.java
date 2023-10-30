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

import com.cpkld.model.entity.Customer;
import com.cpkld.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerApiController {
    
    @Autowired
    private CustomerService service;

    /* TEST /api/v1/customer */
    @GetMapping
    public ResponseEntity<?> readCustomers() {
        return service.getAll();
    }   

    /* /api/v1/customer/customer001 */
    @GetMapping("/{id}")
    public ResponseEntity<?> readCustomer(@PathVariable String id) {
        return service.getById(id);
    }

    /* /api/v1/customer?page={number} */
    @GetMapping(params = "page")
    public ResponseEntity<?> readCustomersPaginated(@RequestParam("page") int page) {
        return service.getPaginated(page);
    }

    /* 
        /api/v1/customer 
        {
            "customerId": "customer010",
            "customerFullname": "Name 10",
            "customerAddress": "Address 10",
            "customerEmail": "name10@gmail.com",
            "phoneNumber": "phonenum10",
            "accountId": "customer001"
        }
    */
    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        return service.add(customer);
    }

    /* 
        /api/v1/customer/customer010 
        {
            "customerId": "customer010",
            "customerFullname": "Name10",
            "customerAddress": "Address10",
            "customerEmail": "name10@gmail.com",
            "phoneNumber": "phonenum10",
            "accountId": "customer001"
        }
    */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(
        @PathVariable String id, 
        @RequestBody Customer customer
    ) {
        return service.update(id, customer);
    }

    /* /api/v1/customer/customer010  */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        return service.delete(id);
    }

}
