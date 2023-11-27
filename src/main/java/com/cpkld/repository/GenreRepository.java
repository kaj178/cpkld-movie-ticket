package com.cpkld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpkld.model.entity.MovieGenre;

@Repository
public interface GenreRepository extends JpaRepository<MovieGenre, Integer> {
    
}
