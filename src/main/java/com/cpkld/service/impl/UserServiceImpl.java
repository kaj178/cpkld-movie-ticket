package com.cpkld.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cpkld.model.entity.User;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.UserRepository;
import com.cpkld.service.UserService;

@Service("account-service")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repo;

    @Override
    public ResponseEntity<?> getAll() {
        List<User> accountList = repo.findAll();
        ApiResponse<User> apiResponse = new ApiResponse<>(200, "Success", accountList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public ResponseEntity<?> getPaginated(int page) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPaginated'");
    }

    @Override
    public ResponseEntity<?> update(Integer id, User account) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
