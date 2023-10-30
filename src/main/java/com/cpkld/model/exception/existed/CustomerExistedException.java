package com.cpkld.model.exception.existed;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CustomerExistedException extends RuntimeException {
    public CustomerExistedException(String msg) {
        super(msg);
    }
}
