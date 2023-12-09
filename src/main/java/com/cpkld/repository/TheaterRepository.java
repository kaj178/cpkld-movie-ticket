package com.cpkld.repository;

import com.cpkld.model.entity.Theater;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Integer> {
    @Query(value = "select * from public.theater "
    , nativeQuery = true)
    Page<Theater> getAllTheater(Pageable pageable);

    @Query(value = "select th.* from public.theater th " +
            "join room r on th.theater_id = r.theater_id " +
            "join showtime st on st.room_id = r.room_id " +
            "join ticket t on t.showtime_id = st.showtime_id " +
            "where t.ticket_id = :ticketId",
    nativeQuery = true)
    Theater getTheaterByTicketId(@Param("ticketId") Integer ticketId);
}
