package com.chamcongtinhluong.contract_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="contract")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idcontract;

    @Column(name = "idemployee",length = 45)
    private String idemployee;

    @Column(name="basicsalary")
    private int basicsalary;

    @Column(name="workingdays")
    private int workingdays;

    @Column(name= "leavedays")
    private int leavedays;

    @Column(name="startdate")
    @Temporal(TemporalType.DATE)
    private Date startdate;

    @Column(name="endate")
    @Temporal(TemporalType.DATE)
    private Date endate;

    @Column(name="status")
    private int status;



}
