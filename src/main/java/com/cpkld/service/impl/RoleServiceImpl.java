package com.cpkld.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cpkld.model.entity.Role;
import com.cpkld.model.exception.notfound.RoleNotFoundException;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.RoleRepository;
import com.cpkld.service.RoleService;

@Service("role-service")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository repo;

    @Override
    public ResponseEntity<?> getAll() {
        List<Role> roles = repo.findAll();
        ApiResponse<Role> apiResponse = new ApiResponse<>(200, "Success", roles);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getById(String id) {
        Optional<Role> roleOptional = repo.findById(id);
        if (!roleOptional.isPresent()) {
            throw new RoleNotFoundException("Role not found");
        }
        List<Role> listRole = roleOptional.stream().toList();
        ApiResponse<Role> apiResponse = new ApiResponse<>(200, "Success", listRole);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addRole(Role role) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addRole'");
    }

    @Override
    public ResponseEntity<?> updateRole(String id, Role role) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRole'");
    }

    @Override
    public ResponseEntity<?> deleteRole(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRole'");
    }
}
