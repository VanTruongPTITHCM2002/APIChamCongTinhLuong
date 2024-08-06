package com.chamcongtinhluong.payroll_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollContractRequest {
    private  String idemployee;
    private int month;
    private int year;
}
