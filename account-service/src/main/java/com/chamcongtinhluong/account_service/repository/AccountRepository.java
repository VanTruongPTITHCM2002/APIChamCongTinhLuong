package com.chamcongtinhluong.account_service.repository;

import com.chamcongtinhluong.account_service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
        Optional<Account> findByUsername(String username);
}
