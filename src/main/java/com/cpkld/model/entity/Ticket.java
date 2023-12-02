package com.cpkld.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ShowTime.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "showtime_id")
    private ShowTime showTime;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Seat.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = TicketStatus.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id")
    @JsonBackReference
    private TicketStatus status;


    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Booking.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
