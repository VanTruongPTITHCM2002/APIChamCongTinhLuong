package com.chamcongtinhluong.employee.exception.advice;

import com.chamcongtinhluong.employee.exception.InvalidEmployeeException;
import com.chamcongtinhluong.employee.respone.ResponeObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidEmployeeException.class)
    public ResponseEntity<?> handleInvalidEmployeeException(InvalidEmployeeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponeObject(HttpStatus.BAD_REQUEST.value(),"Failed add Employee",""));
    }
}
