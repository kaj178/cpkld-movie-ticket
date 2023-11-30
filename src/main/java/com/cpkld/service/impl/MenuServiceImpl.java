package com.cpkld.service.impl;

import com.cpkld.dto.MenuDTO;
import com.cpkld.model.entity.Menu;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.MenuRepository;
import com.cpkld.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuRepository menuRepository;
    @Override
    public ResponseEntity<?> getAll() {
        List<Menu> menus = menuRepository.findAll();
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        menus.stream().map(this::convertEntityToDTO).collect(Collectors.toList())
                ),
                HttpStatus.OK
                );
    }

    private MenuDTO convertEntityToDTO(Menu menu) {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setMenuId(menu.getMenuId());
        menuDTO.setName(menu.name);
        menuDTO.setPrice(menu.getPrice());
        menuDTO.setImgUrl(menu.getImgUrl());
        menuDTO.setDescription(menu.getDescription());

        return menuDTO;
    }
}
