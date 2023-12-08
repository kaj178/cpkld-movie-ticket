package com.cpkld.model.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "promotion", schema = "public")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "promotion_name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_time")
    private LocalDate startTime;

    @Column(name = "end_time")
    private LocalDate endTime;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "img_url")
    private String imgUrl;

    @OneToMany(mappedBy = "promotion",fetch = FetchType.LAZY, targetEntity = Booking.class)
    @JsonManagedReference(value = "booking-promotion")
    private List<Booking> bookings;
}