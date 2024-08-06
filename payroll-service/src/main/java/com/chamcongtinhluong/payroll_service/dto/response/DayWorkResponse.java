package com.chamcongtinhluong.payroll_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DayWorkResponse {
    private String idemployee;
    private int month;
    private int year;
    private float day_work;
}
