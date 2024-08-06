package com.chamcongtinhluong.payroll_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayrollContractResponse {
    private String idemployee;
    private int basicsalary;
    private float dayworks;
}
