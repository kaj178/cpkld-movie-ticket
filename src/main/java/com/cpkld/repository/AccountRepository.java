package com.cpkld.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpkld.model.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {
    
}
