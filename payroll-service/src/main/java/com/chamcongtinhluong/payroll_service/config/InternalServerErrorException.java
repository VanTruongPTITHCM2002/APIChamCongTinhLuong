package com.chamcongtinhluong.payroll_service.config;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends RuntimeException {
    private HttpStatus status;

    public InternalServerErrorException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
