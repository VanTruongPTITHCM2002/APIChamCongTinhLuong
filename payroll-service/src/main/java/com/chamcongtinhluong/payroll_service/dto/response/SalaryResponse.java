package com.chamcongtinhluong.payroll_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryResponse {
    private int monthSalary;
    private int yearSalary;
    private Long amountEmployee;
    private Double total;
}
