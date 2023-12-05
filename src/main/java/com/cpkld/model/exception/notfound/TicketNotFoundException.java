package com.cpkld.model.exception.notfound;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(String msg) {
        super(msg);
    }
}
