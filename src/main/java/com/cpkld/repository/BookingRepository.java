package com.cpkld.repository;

import com.cpkld.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Optional<Booking> findBookingByBookingId(Integer bookingId);
    Optional<List<Booking>> getAllByBookingTime(LocalDateTime bookingTime);
}
