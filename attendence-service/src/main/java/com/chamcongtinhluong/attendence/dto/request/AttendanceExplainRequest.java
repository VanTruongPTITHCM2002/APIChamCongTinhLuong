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
public class AttendanceExplainRequest {
    private String idemployee;
    @Temporal(TemporalType.DATE)
    private Date date;

    private String  checkintime;

    private String checkoutime;
    private String reason;
}
