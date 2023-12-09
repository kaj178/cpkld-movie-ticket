package com.cpkld.service.impl;

import com.cpkld.dto.FormatDTO;
import com.cpkld.model.entity.Format;
import com.cpkld.model.exception.notfound.FormatNotFoundException;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.FormatRepository;
import com.cpkld.service.FormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FormatServiceImpl implements FormatService {
    @Autowired
    private FormatRepository formatRepository;
    
    @Override
    public ResponseEntity<?> getAllFormat() {
        return new ResponseEntity<>(
            new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                formatRepository.findAll().stream().map(this::convertEntityToDTO).collect(Collectors.toList())
            ),
            HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> getFormatById(Integer formatId) {
        Optional<Format> optional = formatRepository.getFormatById(formatId);
        if (optional.isEmpty()) {
            throw new FormatNotFoundException("Format not founded!");
        }
        return new ResponseEntity<>(
            new ApiResponse<>(
                HttpStatus.OK.value(),
                "Success",
                optional.stream().map(this::convertEntityToDTO).collect(Collectors.toList())
            ),
            HttpStatus.OK
        );
    }

    private FormatDTO convertEntityToDTO(Format format) {
        FormatDTO formatDTO = new FormatDTO();
        formatDTO.setFormatId(format.getId());
        formatDTO.setFormatName(format.getName());
        return formatDTO;
    }
}
