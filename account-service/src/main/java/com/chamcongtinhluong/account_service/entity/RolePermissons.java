package com.chamcongtinhluong.account_service.entity;

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
    @MapsId("role")
    @JoinColumn(name = "idrole")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("permissons")
    @JoinColumn(name = "idpermissons")
    private Permissons permissons;
}
