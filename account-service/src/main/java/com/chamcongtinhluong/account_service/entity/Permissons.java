package com.chamcongtinhluong.account_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name= "permissons")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Permissons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpermissons")
    private int idpermissons;

    @Column(name = "namepermisson",length = 100)
    private String namepermisson;

    @Column(name = "description")
    private String description;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "update_at")
    private Date updateAt;

    @OneToMany(mappedBy = "permissons",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RolePermissons> rolePermissonsList;
}
