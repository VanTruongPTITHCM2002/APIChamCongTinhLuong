package com.chamcongtinhluong.auth.repository;

import com.chamcongtinhluong.auth.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
        Optional<Account> findByUsername(String username);
}
