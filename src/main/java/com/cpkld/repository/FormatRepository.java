package com.cpkld.repository;

import com.cpkld.model.entity.Format;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormatRepository extends JpaRepository<Format, Integer> {
    Optional<Format> getFormatById(Integer formatId);
}
