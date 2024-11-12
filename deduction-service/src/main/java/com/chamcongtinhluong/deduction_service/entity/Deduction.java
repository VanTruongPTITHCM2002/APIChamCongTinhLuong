package com.chamcongtinhluong.deduction_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "deduction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Deduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddeduction")
    private int deduction;

    @Column(name = "idemployee")
    private String idEmployee;

    @Column(name = "amount")
    private int amount;

    @Column(name = "deductiontype")
    private String deductionType;

    @Column(name = "reason")
    private String reason;

    @Column(name = "createAt")
    private Date createAt;

    @Column(name = "updateAt")
    private Date updateAt;

    @Column(name = "status")
    private String status;
}
