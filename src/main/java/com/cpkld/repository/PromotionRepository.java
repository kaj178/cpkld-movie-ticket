package com.cpkld.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cpkld.model.entity.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    @Query(
        value = "SELECT * FROM public.promotion WHERE promotion_name = :name",
        nativeQuery = true
    )
    Optional<Promotion> findPromotionByName(@Param("name") String name);
}
