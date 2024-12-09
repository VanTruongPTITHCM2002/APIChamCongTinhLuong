package com.chamcongtinhluong.payroll_service.dto.request;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollRes {
    private List<String> listEmployee;
    private int month;
    private int year;
    @Temporal(TemporalType.DATE)
    private Date datecreated;
    private String status;
}
