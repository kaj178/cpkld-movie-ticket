package com.cpkld.model.exception.notfound;

public class RoomNotFoundException extends RuntimeException{
    public RoomNotFoundException(String msg) {
        super(msg);
    }
}
