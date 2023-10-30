package com.cpkld.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

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

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "role_id", nullable = false)
    // private Role role;
}
