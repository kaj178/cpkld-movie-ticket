package com.cpkld.api.controller;

import com.cpkld.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/menu")
public class MenuApi {
    @Autowired
    private MenuService menuService;

    @GetMapping
    public ResponseEntity<?> readMenus() {
        return menuService.getAll();
    }


}