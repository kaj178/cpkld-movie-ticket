package com.cpkld.repository;

import com.cpkld.model.entity.Theater;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Integer> {


    @Query(value = "select * from public.theater"
    , nativeQuery = true)
    Page<Theater> getAllTheater(Pageable pageable);


}
