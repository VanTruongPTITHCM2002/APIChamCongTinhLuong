package com.chamcongtinhluong.employee.exception.advice;

import com.chamcongtinhluong.employee.exception.InvalidEmployeeException;
import com.chamcongtinhluong.employee.dto.response.ResponeObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidEmployeeException.class)
    public ResponseEntity<?> handleInvalidEmployeeException(InvalidEmployeeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponeObject(HttpStatus.BAD_REQUEST.value(),"Failed add Employee",""));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }
}
