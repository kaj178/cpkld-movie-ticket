package com.cpkld.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cpkld.model.entity.Role;
import com.cpkld.model.exception.RoleNotFoundException;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.RoleRepository;
import com.cpkld.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository repo;

    @Override
    public ResponseEntity<?> getAll() {
        List<Role> roles = repo.findAll();
        ApiResponse<Role> apiResponse = new ApiResponse<>(200, "Success", roles);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
