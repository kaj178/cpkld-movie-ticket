package com.cpkld.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.cpkld.model.exception.existed.CustomerExistedException;
import com.cpkld.model.exception.existed.ManagerExistedException;
import com.cpkld.model.exception.notfound.CustomerNotFoundException;
import com.cpkld.model.exception.notfound.GenreNotFoundException;
import com.cpkld.model.exception.notfound.ManagerNotFoundException;
import com.cpkld.model.exception.notfound.MovieNotFoundException;
import com.cpkld.model.exception.notfound.RoleNotFoundException;
import com.cpkld.model.response.ErrorResponse;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<?> handleRoleNotFoundException(RoleNotFoundException ex, WebRequest req) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> handleCustomerNotFoundException(CustomerNotFoundException ex, WebRequest req) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerExistedException.class)
    public ResponseEntity<?> handleCustomerExistedException(CustomerExistedException ex, WebRequest req) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ManagerNotFoundException.class)
    public ResponseEntity<?> handleManagerNotFoundException(ManagerNotFoundException ex, WebRequest req) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ManagerExistedException.class)
    public ResponseEntity<?> handleManagerExistedException(ManagerExistedException ex, WebRequest req) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<?> handleMovieNotFoundException(MovieNotFoundException ex, WebRequest req) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(GenreNotFoundException.class)
    public ResponseEntity<?> handleGenreNotFoundException(GenreNotFoundException ex, WebRequest req) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
