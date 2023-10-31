package com.cpkld.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "account", schema = "public")
public class Account {

    @Id
    @Column(name = "account_id")
    private String accountId;

    @Column(name = "email")
    private String accountEmail;

    @Column
    private String accountPassword;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Role.class)
    @JoinColumn(name = "role_id", nullable = false)
    @JsonBackReference
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Customer.class, mappedBy = "account")
    @JsonManagedReference
    private List<Customer> customers;

}
