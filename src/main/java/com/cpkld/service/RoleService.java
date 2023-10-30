package com.cpkld.service;

import org.springframework.http.ResponseEntity;

import com.cpkld.model.entity.Role;

public interface RoleService {
    ResponseEntity<?> getAll();

    ResponseEntity<?> getById(String id);

    ResponseEntity<?> addRole(Role role);
    
    ResponseEntity<?> updateRole(String id, Role role);

    ResponseEntity<?> deleteRole(String id); 
}


