package com.cpkld.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu_booking", schema = "public")
public class MenuBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer menuBookingId;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    @JsonBackReference(value = "menu-menu")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    @JsonBackReference(value = "menu-booking")
    private Booking booking;

}
