package com.cpkld.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpkld.model.entity.Promotion;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.PromotionRepository;

@RestController
@RequestMapping("/api/v1/promotion")
public class PromotionApi {

    @Autowired
    private PromotionRepository promotionRepository;

    @GetMapping(params = "promotion_name")
    public ResponseEntity<?> getPromotionName(@RequestParam(name = "promotion_name") String promotionStr) {
        Optional<Promotion> promotion = promotionRepository.findPromotionByName(promotionStr);
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        promotion.stream().toList()),
                HttpStatus.OK);
    }
}
