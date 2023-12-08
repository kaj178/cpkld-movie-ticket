package com.cpkld.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu", schema = "public")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    public Integer menuId;

    @Column(name = "name")
    public String name;

    @Column(name = "price")
    public double price;

    @Column(name = "image_url")
    public String imgUrl;

    @Column(name = "status")
    public Integer status;

    @OneToMany(mappedBy = "menu")
    @JsonBackReference(value = "menu-menu")
    private List<MenuBooking> menuBookings;
}
