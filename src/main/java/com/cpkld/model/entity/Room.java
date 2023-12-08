package com.cpkld.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Theater.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "theater_id")
    @JsonBackReference(value = "room-theater")
    private Theater theater;

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER, targetEntity = Seat.class)
    // @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonBackReference(value = "seat-room")
    private List<Seat> seats;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, targetEntity = ShowTime.class)
    @JsonManagedReference(value = "room-showtime")
    private List<ShowTime> showTimes;
}
