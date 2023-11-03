package com.cpkld.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpkld.model.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    
}
