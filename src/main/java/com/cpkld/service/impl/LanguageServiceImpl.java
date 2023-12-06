package com.cpkld.service.impl;

import com.cpkld.dto.LanguageDTO;
import com.cpkld.dto.ManagerDTO;
import com.cpkld.model.entity.*;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.BookingRepository;
import com.cpkld.repository.LanguageRepository;
import com.cpkld.repository.StudioRepository;
import com.cpkld.repository.TheaterRepository;
import com.cpkld.service.BookingService;
import com.cpkld.service.LanguageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class LanguageServiceImpl implements LanguageService {
    @Autowired
    private LanguageRepository languageRepository;

    private LanguageDTO convertEntityToDto(Language language) {
        LanguageDTO languageDTO = new LanguageDTO();
        languageDTO.setId(language.getId());
        languageDTO.setName(language.getName());
        return languageDTO;
    }

    public ResponseEntity<?> getAll() {
        List<Language> language = languageRepository.findAll();
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        language.stream().map(this::convertEntityToDto)
                                .collect(Collectors.toList())),
                HttpStatus.OK);
    }
}
