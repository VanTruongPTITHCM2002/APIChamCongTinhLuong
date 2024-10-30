package com.chamcongtinhluong.account_service.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data
@Builder
public class RolePermissonsID implements Serializable {
    private int role;
    private int permissons;
}
