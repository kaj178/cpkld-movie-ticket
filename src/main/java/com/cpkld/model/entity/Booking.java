package com.cpkld.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking", schema = "public")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    public Integer bookingId;

    @Column(name = "amount")
    public Integer amount;

    @Column(name = "booking_time")
    public LocalDateTime bookingTime;

    @Column(name = "status")
    public String status;

    @Column(name = "total_price")
    public double totalPrice;
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Promotion.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "promotion_id")
    @JsonBackReference(value = "booking-promotion")
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Customer.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    @JsonBackReference(value = "booking-customer")
    private Customer customer;

    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY, targetEntity = MenuBooking.class)
    @JsonManagedReference(value = "menu-booking")
    private List<MenuBooking> menuBookings;

    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY, targetEntity = Ticket.class)
    @JsonManagedReference(value = "ticket-booking")
    private List<Ticket> tickets;
}
