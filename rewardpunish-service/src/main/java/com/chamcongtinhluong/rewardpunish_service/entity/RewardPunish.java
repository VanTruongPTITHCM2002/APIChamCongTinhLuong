package com.chamcongtinhluong.rewardpunish_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="rewardpunish")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RewardPunish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idrewardpunish;

    @Column(name="idemployee",length = 45)
    private String idemployee;

    @Column(name="type")
    private int type;

    @Column(name="cash")
    private int cash;

    @Column(name="reason")
    private String reason;

    @Column(name = "setupdate")
    @Temporal(TemporalType.DATE)
    private Date setupdate;

    @Column(name="status")
    private int status;
}
