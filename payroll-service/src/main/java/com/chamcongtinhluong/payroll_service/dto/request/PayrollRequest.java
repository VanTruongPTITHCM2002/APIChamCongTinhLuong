package com.chamcongtinhluong.payroll_service.dto.request;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollRequest {
    private String idemployee;
    private int month;
    private int year;
    @Temporal(TemporalType.DATE)
    private Date datecreated;
    private String status;
}
