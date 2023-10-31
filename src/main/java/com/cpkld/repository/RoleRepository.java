package com.cpkld.repository;

// import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

import com.cpkld.model.entity.Role;
import java.util.List;


public interface RoleRepository extends JpaRepository<Role, String> {
    // @EntityGraph(attributePaths = "accounts")
    List<Role> findByRoleId(String roleId);
}
