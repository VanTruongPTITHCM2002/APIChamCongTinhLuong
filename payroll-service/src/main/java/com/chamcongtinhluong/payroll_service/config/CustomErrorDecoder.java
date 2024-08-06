package com.chamcongtinhluong.payroll_service.config;

import feign.Response;
import feign.codec.ErrorDecoder;

import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());
        String message = response.reason();

        // You can customize this part to handle different status codes differently
        switch (status) {
            case BAD_REQUEST:
                return new BadRequestException("Bad Request: " + message, status);

            case INTERNAL_SERVER_ERROR:
                return new InternalServerErrorException("Internal Server Error: " + message, status);
            default:
                return defaultErrorDecoder.decode(methodKey, response);
        }
    }
}
