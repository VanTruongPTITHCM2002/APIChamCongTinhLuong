package com.chamcongtinhluong.attendence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="attendance")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idattendance;

    @Temporal(TemporalType.DATE)
    @Column(name = "dateattendance")
    private Date dateattendance;


    @Column(name="checkintime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private Time checkintime;

    @Column(name="checkouttime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private Time checkouttime;

    @ManyToOne
    @JoinColumn(name="status")
    private AttendanceStatus attendanceStatus;

    @ManyToOne
    @JoinColumn(name="idworkrecord")
    private WorkRecord workRecord;

    @OneToMany(mappedBy = "attendance",orphanRemoval = true)
    private List<AttendanceExplain> attendanceExplainList;

    @Column(name = "numberwork")
    private Float numberwork;

}
