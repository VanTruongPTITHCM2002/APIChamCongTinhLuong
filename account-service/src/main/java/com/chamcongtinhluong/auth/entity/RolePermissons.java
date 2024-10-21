package com.chamcongtinhluong.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role_permissons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePermissons {
    @EmbeddedId
    private RolePermissonsID rolePermissonsID;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idrole")
    @JoinColumn(name = "idrole")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idpermissons")
    @JoinColumn(name = "idpermissons")
    private Permissons permissons;
}
