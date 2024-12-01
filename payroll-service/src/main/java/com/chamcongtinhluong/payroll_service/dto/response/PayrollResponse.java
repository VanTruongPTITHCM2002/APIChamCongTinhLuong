package com.chamcongtinhluong.payroll_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayrollResponse {
    private String idEmployee;
    private int month;
    private int year;
    private int reward;
    private int punish;
    private int basicSalary;
    private float day_work;
    private Date createDate;
    private float totalPayment;
    private String status;
}
