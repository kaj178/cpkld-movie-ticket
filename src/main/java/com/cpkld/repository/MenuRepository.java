package com.cpkld.repository;

import com.cpkld.model.entity.Menu;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Query(value = "select * from public.menu " +
            "where item_id = :menuId ", nativeQuery = true)
    Optional<Menu> getMenuById(Integer menuId);

}
