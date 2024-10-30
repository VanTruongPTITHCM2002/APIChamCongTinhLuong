package com.chamcongtinhluong.account_service.repository;

import com.chamcongtinhluong.account_service.entity.RolePermissons;
import com.chamcongtinhluong.account_service.entity.RolePermissonsID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissonsRepository extends JpaRepository<RolePermissons,RolePermissonsID> {
    List<RolePermissons> findByRolePermissonsID_Permissons(int permissons);
}
