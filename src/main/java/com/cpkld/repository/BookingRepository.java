package com.cpkld.repository;

import com.cpkld.model.entity.Booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Optional<Booking> findBookingByBookingId(Integer bookingId);

    @Query(value = "SELECT booking_id FROM public.booking ORDER BY booking_id DESC LIMIT 1", nativeQuery = true)
    public int getlastid();

}
