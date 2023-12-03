package com.cpkld.repository;

import com.cpkld.model.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {


    @Query(value = "select sea.* from public.seat as sea " +
            "join public.ticket as tic on sea.seat_id = tic.seat_id " +
            "where tic.ticket_id = :ticketId ",
    nativeQuery = true)
    Optional<List<Seat>> getSeatByTicket(@Param("ticketId") Integer ticketId);


}
