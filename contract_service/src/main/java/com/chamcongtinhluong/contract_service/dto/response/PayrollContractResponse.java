package com.chamcongtinhluong.contract_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayrollContractResponse {
    private String idemployee;
    private int basicsalary;
    private float dayworks;
}
