package com.chamcongtinhluong.attendence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name="attendance_explain")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AttendanceExplain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idattendance_explain;

    @ManyToOne
    @JoinColumn(name="idattendance")
    private Attendance attendance;

    @Column(name="explaination",nullable = false)
    private String explaination;

    @Column(name="status")
    private int status;
}
