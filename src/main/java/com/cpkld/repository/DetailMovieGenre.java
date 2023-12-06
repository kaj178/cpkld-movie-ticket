package com.cpkld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailMovieGenre extends JpaRepository<DetailMovieGenre, Integer> {
}
