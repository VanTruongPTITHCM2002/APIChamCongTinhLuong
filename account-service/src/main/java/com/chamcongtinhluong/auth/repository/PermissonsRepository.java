package com.chamcongtinhluong.auth.repository;

import com.chamcongtinhluong.auth.entity.Permissons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissonsRepository extends JpaRepository<Permissons,Integer> {

    Boolean existsByNamepermisson (String namepermisson);
}
