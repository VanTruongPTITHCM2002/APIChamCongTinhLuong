package com.chamcongtinhluong.payroll_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="payroll")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idpayroll;

    @Column(name = "idemployee",length = 45)
    private String idemployee;

    @Column(name="name",length = 100)
    private String name;

    @Column(name = "month")
    private int month;

    @Column(name="year")
    private int year;

    @Column(name="basicsalary")
    private int basicsalary;

    @Column(name = "day_work",nullable = false)
    private float day_work;

    @Column(name="reward")
    private int reward;

    @Column(name = "punish")
    private int punish;

    @Column(name="datecreated")
    @Temporal(TemporalType.DATE)
    private Date datecreated;

    @Column(name="totalpayment")
    private float totalpayment;

    @Column(name = "status")
    private int status;


}
