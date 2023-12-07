package com.cpkld.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Entity
@Table(name = "format", schema = "public")
public class Format {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "format_id")
    private Integer id;

    @Column(name = "format_name")
    private String name;

    @OneToMany(mappedBy = "format", fetch = FetchType.LAZY, targetEntity = ShowTime.class)
    @JsonBackReference
    private List<ShowTime> showTimes;
}
