package com.chamcongtinhluong.leaverequest_service.entity;

import com.chamcongtinhluong.leaverequest_service.Enum.LeaveType;
import com.chamcongtinhluong.leaverequest_service.Enum.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "leavetype")
    private LeaveType leaveType;

    @Column(name = "startdate")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "endate")
    @Temporal(TemporalType.DATE)
    private Date endate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "reason")
    private String reason;

    @Column(name = "createAt")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date createAt;

    @Column(name = "approveby",length = 45)
    private String approveBy;

    @Column(name = "approveAt")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date approveAt;
}
