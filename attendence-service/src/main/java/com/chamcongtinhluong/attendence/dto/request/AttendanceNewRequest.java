package com.chamcongtinhluong.attendence.dto.request;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceNewRequest {
    private String idEmployee;
    @Temporal(TemporalType.DATE)
    private Date dateAttendance;
    private String timeWork;
}
