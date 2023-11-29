package com.cpkld.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket", schema = "public")
public class Ticket {
    @Id
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer ticketId;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = ShowTime.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "showtime_id")
    private ShowTime showTime;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY, targetEntity = Seat.class)
    private List<Seat> seats;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = TicketStatus.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id")
    private TicketStatus status;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Customer.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Booking.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id")
    @JsonBackReference
    private Booking booking;
}
