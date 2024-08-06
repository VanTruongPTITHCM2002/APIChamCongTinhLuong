package com.chamcongtinhluong.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private String idemployee;

    private String firstname;

    private String lastname;

    private String gender;

    private Date birthdate;

    private String cmnd;

    private String email;

    private String phonenumber;

    private String address;

    private String degree;

    private String status;


}
