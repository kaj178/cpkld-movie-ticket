package com.cpkld.service;

import com.cpkld.dto.MenuDTO;
import org.springframework.http.ResponseEntity;

public interface MenuService {
    ResponseEntity<?> getAll();

    ResponseEntity<?> getMenuById(int menuDTO);

    ResponseEntity<?> add(MenuDTO menuDTO);

    ResponseEntity<?> update(Integer id, MenuDTO menuDTO);
}
