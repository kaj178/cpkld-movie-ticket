package com.cpkld.api.controller;

import com.cpkld.dto.MenuDTO;
import com.cpkld.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menu")
public class MenuApi {
    @Autowired
    private MenuService menuService;

    @GetMapping(params = "menu_id")
    public ResponseEntity<?> getMenuByID(@RequestParam(name = "menu_id") Integer menuID) {
        return menuService.getMenuById(menuID);
    }

    @GetMapping
    public ResponseEntity<?> readMenus() {
        return menuService.getAll();
    }

    @PostMapping
    public ResponseEntity<?> createMenu(@RequestBody MenuDTO menuDTO) {
        return menuService.add(menuDTO);
    }

}
