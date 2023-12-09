package com.cpkld.repository;

import com.cpkld.model.entity.Ticket;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Query(value = "select * from ticket t " +
            "where t.showtime_id = :showtimeId ", 
        nativeQuery = true)
    Optional<List<Ticket>> getTicketsByShowTimeId(@Param("showtimeId") Integer showTimeId);

    @Query(value = "select t.* from public.ticket t " +
            "join public.booking b on t.booking_id = b.booking_id " +
            "where b.booking_id = :bookingId ",
            nativeQuery = true)
    Optional<List<Ticket>> getTicketsByBookingId(@Param("bookingId") Integer bookingId);

    @Query(value = "select c.email from public.ticket t " +
            "join public.booking b on b.booking_id = t.booking_id " +
            "join public.customer c on c.customer_id = b.booking_id " +
            "where t.ticket_id = :ticketId " ,
    nativeQuery = true)
    String getEmailCustomerByTicket(@Param("ticketId") Integer ticketId);
    
    @Modifying
    @Transactional
    @Query(
        value = "INSERT INTO public.ticket (booking_id, seat_id, showtime_id, status_id) " + 
                "VALUES (:booking_id, :seat_id, :showtime_id, :status_id)", 
        nativeQuery = true
    )
    void saveTicket(
        @Param("booking_id") Integer bookingId, 
        @Param("seat_id") Integer seatId, 
        @Param("showtime_id") Integer showTimeId,
        @Param("status_id") Integer statusId
    );
}
