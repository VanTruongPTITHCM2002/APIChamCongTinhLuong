package com.chamcongtinhluong.account_service.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseObject {
    private int status;
    private String message;
    private Object data;
}
