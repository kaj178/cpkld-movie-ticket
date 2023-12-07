package com.cpkld.repository;

import com.cpkld.model.entity.Booking;
import com.cpkld.model.entity.Menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Optional<Booking> findBookingByBookingId(Integer bookingId);

    @Query(value = "SELECT * FROM public.booking where customer_id = :customer_id", nativeQuery = true)
    public int getBookingByCustomerID(@Param("customer_id") Integer customer_id);

}
