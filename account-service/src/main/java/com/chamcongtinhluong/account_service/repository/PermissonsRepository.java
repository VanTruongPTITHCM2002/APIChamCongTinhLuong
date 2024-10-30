package com.chamcongtinhluong.account_service.repository;

import com.chamcongtinhluong.account_service.entity.Permissons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissonsRepository extends JpaRepository<Permissons,Integer> {

    Boolean existsByNamepermisson (String namepermisson);
    Permissons findByNamepermisson(String namepermisson);
}
