package com.cpkld.repository;

import com.cpkld.model.entity.Booking;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Optional<Booking> findBookingByBookingId(Integer bookingId);

    @Query(value = "SELECT * FROM public.booking", nativeQuery = true)
    public List<Booking> getAllBooking();

    @Query(value = "SELECT * FROM public.booking where customer_id = :customer_id", nativeQuery = true)
    public List<Booking> getBookingByCustomerID(@Param("customer_id") Integer customer_id);

    @Query(value = "SELECT booking_id FROM public.booking ORDER BY booking_id DESC LIMIT 1", nativeQuery = true)
    public int getLastId();

    @Modifying
    @Transactional
    @Query(
        value = "INSERT INTO public.booking (amount, booking_time, status, total_price, promotion_id, customer_id) " +
                "VALUES (:amount, :booking_time, :status, :total_price, :promotion_id, :customer_id)", 
        nativeQuery = true
    )
    void saveBooking(
        @Param("amount") Integer amount,
        @Param("booking_time") LocalDateTime bookingTime,
        @Param("status") String status,
        @Param("total_price") double totalPrice,
        @Param("promotion_id") Integer promotionId,
        @Param("customer_id") Integer customerId
    );

}
