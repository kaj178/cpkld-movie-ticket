package com.cpkld.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Table(name = "language", schema = "public")
public class Language {
    @Id
    @Column(name = "language_id")
    private String languageId;
    @Column(name = "language_name")
    private String languageName;

}
