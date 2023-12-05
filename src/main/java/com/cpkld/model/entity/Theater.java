package com.cpkld.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "theater", schema = "public")
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theater_id")
    private Integer id;

    @Column(name = "theater_name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phoneNumber;

    @Column(name = "number_of_room")
    private Integer numberOfRooms;


    @OneToMany(mappedBy = "theater", fetch = FetchType.LAZY, targetEntity = Room.class)
    @JsonBackReference
    private List<Room> rooms;
}