package com.cpkld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpkld.model.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    
}
