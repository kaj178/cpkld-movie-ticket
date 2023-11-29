package com.cpkld.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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
    private List<ShowTime> showTimes;
}
