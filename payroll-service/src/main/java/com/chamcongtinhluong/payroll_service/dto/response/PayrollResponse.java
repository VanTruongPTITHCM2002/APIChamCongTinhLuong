package com.chamcongtinhluong.payroll_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayrollResponse {
    private String idemployee;
    private String name;
    private int month;
    private int year;
    private int reward;
    private int punish;
    private int basicsalary;
    private float day_work;
    private Date datecreated;
    private float totalpayment;
    private String status;
}
