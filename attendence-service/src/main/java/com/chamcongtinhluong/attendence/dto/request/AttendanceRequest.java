package com.chamcongtinhluong.attendence.dto.request;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceRequest {
    private String idemployee;
    @Temporal(TemporalType.DATE)
    private Date dateattendance;
    private String checkintime;
    private String checkouttime;
    private String status;
    private float numberwork;
}
