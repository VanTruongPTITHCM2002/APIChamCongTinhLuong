package com.chamcongtinhluong.employee.respone;

import com.chamcongtinhluong.employee.entity.Employee;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponeObject {
    private int status;
    private String message;
    private Object data;
}
