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
    private String idemployee;
    private String name;
    private Date workdate;
    private LocalTime startime;
    private LocalTime endtime;
}
