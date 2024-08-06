package com.chamcongtinhluong.employee.respone;

import com.chamcongtinhluong.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponeObject {
    private int status;
    private String message;
    private Object data;


}
