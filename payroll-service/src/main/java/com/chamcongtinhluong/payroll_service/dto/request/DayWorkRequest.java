package com.chamcongtinhluong.payroll_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DayWorkRequest {
    private String idemployee;
    private int month;
    private int year;
}
