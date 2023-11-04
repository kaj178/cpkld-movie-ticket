package com.cpkld.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpkld.service.RoleService;

@RestController
@RequestMapping("/api/v1/role")
public class RoleApi {
    @Autowired
    private RoleService service;

    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Integer id) {
        return service.getById(id);
    }
}
