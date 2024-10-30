package com.chamcongtinhluong.account_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idrole;

    @Column(name = "rolename",length = 100)
    private String rolename;

    @OneToMany(mappedBy = "roles",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accountList;

    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RolePermissons> rolePermissons;
}
