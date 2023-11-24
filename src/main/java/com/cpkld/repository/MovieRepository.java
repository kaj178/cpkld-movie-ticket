package com.cpkld.repository;

import com.cpkld.model.entity.Movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    //@Query("SELECT m FROM Movie m JOIN FETCH m.language")\
    @Query(
        value = "SELECT mv.*, lg.language_name " + 
                "FROM public.movie as mv " + 
                "JOIN public.language as lg " + 
                "ON mv.language_id = lg.language_id",
        nativeQuery = true
    )
    Page<Movie> findAllMovies(Pageable pageable);
}
