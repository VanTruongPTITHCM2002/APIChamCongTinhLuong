package com.chamcongtinhluong.employee.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResponse {
    private String idEmployee;
    private String firstName;
    private String lastName;
    private String gender;
    private Date birthDate;
    private String idCard;
    private String email;
    private String phoneNumber;
    private String address;
    private String degree;
    private String department;
    private String position;
    private String status;
    private byte[] image;
}
