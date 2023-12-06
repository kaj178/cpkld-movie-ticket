package com.cpkld.repository;

import com.cpkld.model.entity.Language;
import com.cpkld.model.entity.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface LanguageRepository extends JpaRepository<Language, Integer> {

}
