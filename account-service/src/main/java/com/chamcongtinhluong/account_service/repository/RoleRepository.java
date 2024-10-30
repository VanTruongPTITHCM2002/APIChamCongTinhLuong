package com.chamcongtinhluong.account_service.repository;

import com.chamcongtinhluong.account_service.entity.Account;
import com.chamcongtinhluong.account_service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    @Query("SELECT a FROM Account a JOIN a.roles r WHERE r.rolename = :rolename")
    List<Account> findByRole(@Param("rolename") String rolename);

    Role findByRolename(String rolename);
}
