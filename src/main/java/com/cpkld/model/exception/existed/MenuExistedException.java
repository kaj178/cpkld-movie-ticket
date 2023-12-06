package com.cpkld.model.exception.existed;

public class MenuExistedException extends RuntimeException{
    public MenuExistedException(String msg) {
        super(msg);
    }
}
