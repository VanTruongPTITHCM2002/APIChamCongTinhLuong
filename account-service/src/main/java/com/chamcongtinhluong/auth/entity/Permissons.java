package com.chamcongtinhluong.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private int id;

    @Column(name = "namepermissons",length = 100)
    private String permissName;

    @Column(name = "description")
    private String description;

    @Column(name = "createAt")
    private Date createAt;

    @Column(name = "updateAt")
    private Date updateAt;
}
