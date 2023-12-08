package com.cpkld.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "showtime", schema = "public")
public class ShowTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showtime_id")
    private Integer id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "status")
    private String status;

    @Column(name = "price")
    private double price;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Movie.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    @JsonBackReference(value = "showtime-movie")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Format.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "format_id")
    @JsonBackReference(value = "showtime-format")
    private Format format;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Room.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    @JsonBackReference(value = "room-showtime")
    private Room room;

    @OneToMany(mappedBy = "showTime", fetch = FetchType.LAZY, targetEntity = Ticket.class)
    @JsonBackReference(value = "ticket-showtime")
    private List<Ticket> tickets;
}
