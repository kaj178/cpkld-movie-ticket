package com.cpkld.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cpkld.model.entity.Customer;

import jakarta.transaction.Transactional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO public.customer (full_name, address, email, phone_number, user_id) VALUES (:fullname, :address, :email, :phone, :userId)", nativeQuery = true)
    void saveCustomer(
        @Param("fullname") String fullname,
        @Param("address") String address,
        @Param("email") String email, 
        @Param("phone") String phone, 
        @Param("userId") Integer userId
    );

    Optional<Customer> findByEmail(String email);
}
