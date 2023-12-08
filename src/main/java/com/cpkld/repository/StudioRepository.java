package com.cpkld.repository;

import com.cpkld.model.entity.Studio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Integer> {
    @Query(value = "SELECT * FROM public.studio WHERE studio_id = :id", nativeQuery = true)
    Optional<Studio> findStudioById(@Param("id") int id);
}
