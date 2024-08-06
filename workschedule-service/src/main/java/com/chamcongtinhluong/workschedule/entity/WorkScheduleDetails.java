package com.chamcongtinhluong.workschedule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="work_schedule_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkScheduleDetails {
    @EmbeddedId
    private EmployeeWorkScheduleId employeeWorkScheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("workschedule")
    @JoinColumn(name = "idwork_schedule")
    private WorkSchedule workSchedule;



}
