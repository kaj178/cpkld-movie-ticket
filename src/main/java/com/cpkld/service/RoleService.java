package com.cpkld.service;

import org.springframework.http.ResponseEntity;

import com.cpkld.model.entity.Role;

public interface RoleService {
    ResponseEntity<?> getAll();

    ResponseEntity<?> getById(Integer id);

    ResponseEntity<?> addRole(Role role);
    
    ResponseEntity<?> updateRole(Integer id, Role role);

    ResponseEntity<?> deleteRole(Integer id); 
}


