package com.chamcongtinhluong.attendence.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class AttendanceResponse {
    private String idemployee;
    private Date dateattendance;
    private String checkintime;
    private String checkouttime;
    private String attendanceStatusName;
    private String description;
    private float numberwork;
}
