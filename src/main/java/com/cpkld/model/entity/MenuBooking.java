package com.cpkld.model.entity;

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
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

}
