package com.cpkld.repository;

import com.cpkld.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Query(value = "select * from ticket t" +
            "where t.showtime_id = 1"
    , nativeQuery = true)
    Optional<List<Ticket>> getTicketsByShowTimeId(Integer showTimeId);
}
