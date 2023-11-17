package com.cpkld.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cpkld.model.entity.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Transactional
    // @Query("INSERT INTO User(email, password, status, role) VALUES (:#{#user.email}, :#{#user.password}, :#{#user.status}, :#{#user.role})")
    @Query(value = "INSERT INTO public.user (email, password, status, role_id) VALUES (:email, :password, :status, :roleId)", nativeQuery = true)
    void saveUser(
        @Param("email") String email, 
        @Param("password") String password, 
        @Param("status") Integer status, 
        @Param("roleId") Integer roleId
    );
    
    @EntityGraph(attributePaths = "role")
    User findByEmail(String email);
}
