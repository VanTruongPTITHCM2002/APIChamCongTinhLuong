package com.chamcongtinhluong.account_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idaccount;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "create_at")
    private Date create_at;

    @ManyToOne
    @JoinColumn(name = "idrole")
    private Role roles;

    @Column(name = "status")
    private int status;

}
