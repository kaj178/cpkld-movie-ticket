package com.cpkld.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cpkld.model.entity.Manager;

import jakarta.transaction.Transactional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO public.manager (full_name, address, email, phone_number, user_id) VALUES (:fullname, :address, :email, :phone, :userId)", nativeQuery = true)
    void saveManager(
        @Param("fullname") String fullname,
        @Param("address") String address,
        @Param("email") String email, 
        @Param("phone") String phone, 
        @Param("userId") Integer userId
    );

    @EntityGraph(attributePaths = "user")
    Optional<Manager> findByEmail(String email);
}
