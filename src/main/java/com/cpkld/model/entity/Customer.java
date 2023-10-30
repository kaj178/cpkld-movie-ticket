package com.cpkld.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

    // @Column(name = "account_id")
    // @JoinColumn
    @Column(name = "account_id")
    private String accountId;
}
