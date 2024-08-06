package com.chamcongtinhluong.attendence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="attendance_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idattendance_status;

    @Column(name="attendance_status_name",length = 45)
    private String attendanceStatusName;

    @Column(name="description",nullable = false)
    private String description;

    @OneToMany(mappedBy = "attendanceStatus",orphanRemoval = true)
    private List<Attendance> attendanceList;
}
