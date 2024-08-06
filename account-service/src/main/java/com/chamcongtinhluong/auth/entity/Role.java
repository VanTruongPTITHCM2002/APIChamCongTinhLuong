package com.chamcongtinhluong.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idrole;

    private String rolename;

    @OneToMany(mappedBy = "roles",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accountList;
}
