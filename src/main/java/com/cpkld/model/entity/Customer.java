package com.cpkld.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "customer", schema = "public")
public class Customer {
    @Id
    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "full_name")
    private String customerFullname;

    @Column(name = "address")
    private String customerAddress;

    @Column(name = "email")
    private String customerEmail;

    @Column(name = "phone_number")
    private String phoneNumber;

    // @Column(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;
}
