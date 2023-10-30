package com.cpkld.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cpkld.model.entity.Account;
import com.cpkld.model.response.ApiResponse;
import com.cpkld.repository.AccountRepository;
import com.cpkld.service.AccountService;

@Service("account-service")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository repo;

    @Override
    public ResponseEntity<?> getAll() {
        List<Account> accountList = repo.findAll();
        ApiResponse<Account> apiResponse = new ApiResponse<>(200, "Success", accountList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public ResponseEntity<?> getPaginated(int page) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPaginated'");
    }

    @Override
    public ResponseEntity<?> update(String id, Account account) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
