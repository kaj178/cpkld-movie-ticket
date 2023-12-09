package com.cpkld.service.impl;

import com.cpkld.dto.MenuDTO;
import com.cpkld.model.entity.Menu;
import com.cpkld.model.exception.existed.MenuExistedException;
import com.cpkld.model.exception.notfound.ShowTimeNotFoundException;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.MenuRepository;
import com.cpkld.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
                        menus.stream().map(this::convertEntityToDTO).collect(Collectors.toList())),
                HttpStatus.OK);
    }

    public ResponseEntity<?> getMenuById(int menuid) {
        Optional<Menu> optional = menuRepository.getMenuById(menuid);
        if (optional.isEmpty()) {
            throw new ShowTimeNotFoundException("Menu not found!");
        }
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Success",
                        optional.stream().map(this::convertEntityToDTO).toList()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> add(MenuDTO menuDTO) {
        Menu menu = new Menu();
        menu.setName(menuDTO.getName());
        menu.setPrice(menuDTO.getPrice());
        menu.setImgUrl(menuDTO.getImgUrl());
        menu.setStatus(menuDTO.getStatus());
        menuRepository.save(menu);
        List<Menu> menus = new ArrayList<>();
        return new ResponseEntity<>(
                new ApiResponse<>(
                        HttpStatus.CREATED.value(),
                        "Success",
                        menus),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> update(Integer id, MenuDTO menuDTO) {
        Optional<Menu> optional = menuRepository.findById(id);
        if (optional.isPresent()) {
            throw new MenuExistedException("Menu is existed!");
        }
        Menu menu = new Menu();
        menu.setMenuId(id);
        menu.setName(menuDTO.getName());
        menu.setPrice(menuDTO.getPrice());
        menu.setImgUrl(menuDTO.getImgUrl());
        menu.setStatus(menuDTO.getStatus());
        return null;
    }

    private MenuDTO convertEntityToDTO(Menu menu) {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setItemId(menu.getMenuId());
        menuDTO.setName(menu.getName());
        menuDTO.setPrice(menu.getPrice());
        menuDTO.setImgUrl(menu.getImgUrl());
        menuDTO.setStatus(menu.getStatus());
        return menuDTO;
    }
}
