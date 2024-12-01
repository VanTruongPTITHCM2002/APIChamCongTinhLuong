package com.chamcongtinhluong.workschedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkScheduleDetailRequest {
    private String idEmployee;
    private Date workdate;
    private LocalTime startTime;
    private LocalTime endTime;
}
