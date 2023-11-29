package com.cpkld.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Menu.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id")
    @JsonBackReference
    private Menu menu;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Ticket.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_id")
    @JsonBackReference
    private Ticket ticket;
}
