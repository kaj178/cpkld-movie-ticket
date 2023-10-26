package com.cpkld.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpkld.model.entity.Role;
import com.cpkld.repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private RoleRepository repo;

    public List<Role> getAllRole() {
        return repo.findAll();
    }
}
