package com.chamcongtinhluong.account_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USERNAME_NULL("Vui long khong bo trong tai khoan", HttpStatus.BAD_REQUEST);

    ErrorCode( String message, HttpStatusCode statusCode) {

        this.message = message;
        this.statusCode = statusCode;
    }

    private final String message;
    private final HttpStatusCode statusCode;
}
