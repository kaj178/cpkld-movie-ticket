package com.cpkld.repository;

// import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cpkld.model.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Integer> {
    // @EntityGraph(attributePaths = "accounts")
    Role findByRoleId(String roleId);
}
