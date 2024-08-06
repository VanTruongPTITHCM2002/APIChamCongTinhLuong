package com.chamcongtinhluong.attendence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="workrecord")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idworkrecord;

    @Column(name="idemployee",length = 45)
    private String idemployee;

    @Column(name="month")
    private int month;

    @Column(name="year")
    private int year;

    @Column(name="day_work",nullable = false)
    private float day_work;

    @OneToMany(mappedBy = "workRecord",orphanRemoval = true)
    private List<Attendance> attendanceList;
}
