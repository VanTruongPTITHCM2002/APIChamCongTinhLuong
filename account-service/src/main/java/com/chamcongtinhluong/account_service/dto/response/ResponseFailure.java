package com.chamcongtinhluong.account_service.dto.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


public class ResponseFailure extends  ResponseSuccess{
    public ResponseFailure(){};

    public ResponseFailure(HttpStatus status, String message) {
        super(status, message);
    }
}
