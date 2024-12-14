package com.chamcongtinhluong.notification_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "auditlog")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idauditlog;

    @Column(name = "username",length = 45)
    private String username;

    @Column(name = "action",length = 45)
    private String action;

    @Column(name = "decription",length = 255)
    private String description;

    @Column(name = "createtime")
    private Date createtime;
}
