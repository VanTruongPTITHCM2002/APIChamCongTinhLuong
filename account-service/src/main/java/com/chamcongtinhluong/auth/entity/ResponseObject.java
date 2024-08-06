package com.chamcongtinhluong.auth.entity;


import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseObject {
    private int status;
    private String message;
    private Object data;
}
