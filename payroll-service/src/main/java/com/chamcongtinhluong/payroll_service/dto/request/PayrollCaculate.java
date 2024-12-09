package com.chamcongtinhluong.payroll_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollCaculate {
    @JsonProperty("listEmployee")
    private List<String> listEmployee;
    private PayrollRes payrollRes;
}
