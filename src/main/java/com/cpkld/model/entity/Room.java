package com.cpkld.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Entity
@Table(name = "room", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", insertable = false, updatable = false)
    public Integer roomId;
    @Column(name = "room_name")
    public String roomName;
    @Column(name = "amount_seat")
    public int amountSeats;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Theater.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Seat.class)
    @JoinColumn(name = "seat_id", nullable = false)
    private List<Seat> seats;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, targetEntity = ShowTime.class)
    private List<ShowTime> showTimes;
}
