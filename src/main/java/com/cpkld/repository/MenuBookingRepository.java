package com.cpkld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cpkld.model.entity.MenuBooking;

public interface MenuBookingRepository extends JpaRepository<MenuBooking, Integer> {
    @Query(
        value = "INSERT INTO public.menu_booking (booking_id, menu_id) VALUES (:booking_id, :menu_id)",
        nativeQuery = true
    )
    void saveMenuBooking(@Param("booking_id") Integer bookingId, @Param("menu_id") Integer menuId);
}
