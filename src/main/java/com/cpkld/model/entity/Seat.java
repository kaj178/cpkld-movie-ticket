package com.cpkld.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seat", schema = "public")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id", insertable = false, updatable = false)
    private Integer seatId;
    @Column(name = "seat_name")
    private String seatName;
    @Column(name = "type")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Room.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    @JsonBackReference(value = "seat-room")
    private Room room;

    @OneToMany(mappedBy = "seat", fetch = FetchType.LAZY, targetEntity = Ticket.class)
    @JsonBackReference(value = "ticket-seat")
    private List<Ticket> tickets;

}
