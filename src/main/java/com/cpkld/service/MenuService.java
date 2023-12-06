package com.cpkld.service;

import com.cpkld.dto.MenuDTO;
import com.cpkld.model.entity.Customer;
import com.cpkld.model.entity.Menu;
import org.springframework.http.ResponseEntity;

public interface MenuService {
    ResponseEntity<?> getAll();

    ResponseEntity<?> add(MenuDTO menuDTO);

    ResponseEntity<?> update(Integer id, MenuDTO menuDTO);

//    ResponseEntity<?> delete(Integer id);
}
