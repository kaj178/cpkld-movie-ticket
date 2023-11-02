package com.cpkld.repository;

// import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpkld.model.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    // @EntityGraph(attributePaths = "accounts")
    Role findByRoleId(Integer roleId);
}
