package com.cpkld.model.entity;

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
    private List<MenuBooking> menuBookings;
}
