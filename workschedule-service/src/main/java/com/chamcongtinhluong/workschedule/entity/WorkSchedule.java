package com.chamcongtinhluong.workschedule.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="work_schedule")
@Data
public class WorkSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idwork_schedule;

    @Temporal(TemporalType.DATE)
    @Column(name="workdate")
    private Date workdate;

    @Column(name="startime")
    @JsonFormat(pattern = "HH:mm") // Uncomment if necessary
    private LocalTime startime;

    @Column(name="endtime")
    @JsonFormat(pattern = "HH:mm") // Uncomment if necessary
    private LocalTime endtime;


}
