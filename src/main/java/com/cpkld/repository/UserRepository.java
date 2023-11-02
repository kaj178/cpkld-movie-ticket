package com.cpkld.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpkld.model.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
