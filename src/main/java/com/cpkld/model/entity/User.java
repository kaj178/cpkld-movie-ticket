package com.cpkld.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
// import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "user", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private Integer status;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinColumn(name = "role_id", nullable = false)
    @JsonBackReference
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = Manager.class, mappedBy = "user")
    private Manager manager;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = Customer.class, mappedBy = "user")
    private Customer customer;

}
