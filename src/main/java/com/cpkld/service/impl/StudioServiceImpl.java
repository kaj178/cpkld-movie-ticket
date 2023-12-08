package com.cpkld.service.impl;

import com.cpkld.dto.StudioDTO;
import com.cpkld.model.entity.Studio;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.StudioRepository;
import com.cpkld.service.StudioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudioServiceImpl implements StudioService {
    @Autowired
    private StudioRepository studioRepository;

    public StudioDTO convertEntityToDTO(Studio studio) {
        StudioDTO studioDTO = new StudioDTO();
        studioDTO.setId(studio.getId());
        studioDTO.setName(studio.getName());
        studioDTO.setAddress(studio.getAddress());
        studioDTO.setEmail(studio.getEmail());
        studioDTO.setPhoneNumber(studio.getPhoneNumber());
        return studioDTO;
    }

    public ResponseEntity<?> getAll() {
        List<Studio> studio = studioRepository.findAll();
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        studio.stream().map(this::convertEntityToDTO)
                                .collect(Collectors.toList())),
                HttpStatus.OK);
    }

    public Studio getByID(Integer id) {
        Studio studio = studioRepository.findStudioById(id).get();
        return studio;
    }
}
