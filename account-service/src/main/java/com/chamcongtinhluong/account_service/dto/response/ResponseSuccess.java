package com.chamcongtinhluong.account_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

@Builder
public class ResponseSuccess extends ResponseEntity {

    public ResponseSuccess() {
        super(new Payload(HttpStatus.OK.value(), "Success"), HttpStatus.OK);
    }

    public ResponseSuccess(HttpStatus status, String message) {
        super(new Payload(status.value(), message), HttpStatus.OK);
    }

    public ResponseSuccess(HttpStatus status, String message, Object data) {
        super(new Payload(status.value(), message, data), status);
    }

    public ResponseSuccess(Payload body, HttpStatus status) {
        super(body, status);
    }

    public ResponseSuccess(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    public ResponseSuccess(Payload payload, MultiValueMap<String, String> headers, int rawStatus) {
        super(payload, headers, rawStatus);
    }

    public ResponseSuccess(Payload payload, MultiValueMap<String, String> headers, HttpStatus status) {
        super(payload, headers, status);
    }


    public static class Payload{
        private final int status;
        private final String message;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Object data;



        public Payload(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public Payload(int status, String message, Object data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }
        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public Object getData() {
            return data;
        }
    }
}
