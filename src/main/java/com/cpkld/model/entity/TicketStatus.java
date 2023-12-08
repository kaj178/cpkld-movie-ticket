package com.cpkld.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ticket_status")
public class TicketStatus {
    @Id
    @Column(name = "status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statusId;

    @Column(name = "status_name")
    private String statusName;

    @OneToMany(mappedBy = "status", fetch = FetchType.LAZY, targetEntity = Ticket.class)
    @JsonBackReference(value = "ticket-ticketstatus")
    private List<Ticket> tickets;
}
