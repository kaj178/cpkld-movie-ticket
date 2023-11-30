package com.cpkld.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    public int amount;

    @Column(name = "total_price")
    public double totalPrice;

    @Column(name = "booking_time")
    public LocalDateTime bookingTime;

    @Column(name = "status")
    public String status;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Promotion.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "promotion_id")
    @JsonBackReference
    private Promotion promotion;

    @OneToMany(mappedBy = "booking")
    private List<MenuBooking> menuBookings;


    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY, targetEntity = Ticket.class)
    private List<Ticket> tickets;
}
