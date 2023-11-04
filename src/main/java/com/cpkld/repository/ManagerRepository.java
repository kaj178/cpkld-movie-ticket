package com.cpkld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpkld.model.entity.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    
}
