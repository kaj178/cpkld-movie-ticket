package com.cpkld.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu", schema = "public")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    public Integer menuId;

    @Column(name = "name")
    public String name;

    @Column(name = "price")
    public double price;

    @Column(name = "img_url")
    public String imgUrl;

    @Column(name = "status")
    public String status;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY, targetEntity = Booking.class)
    @JsonBackReference
    private List<Booking> bookings;
}
