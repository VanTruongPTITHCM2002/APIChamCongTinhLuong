package com.chamcongtinhluong.leaverequest_service.entity;

import com.chamcongtinhluong.leaverequest_service.Enum.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "leaverequest")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idleaverequest")
    private int idLeaveRequest;

    @Column(name = "idemployee",length = 45)
    private String idEmployee;

    @Column(name = "leavetype")
    private String leaveType;

    @Column(name = "startdate")
    private Date startDate;

    @Column(name = "endate")
    private Date endate;

    @Column(name = "totaldays")
    private int totalDays;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "reason")
    private String reason;

    @Column(name = "createAt")
    private Date createAt;

    @Column(name = "approveby",length = 45)
    private String approveBy;

    @Column(name = "approveAt")
    private Date approveAt;
}
