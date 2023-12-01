package com.cpkld.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    private Integer Id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Movie.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id", insertable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Format.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "format_id")
    private Format format;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Room.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "showTime", fetch = FetchType.LAZY, targetEntity = Ticket.class)
    private List<Ticket> tickets;
}
