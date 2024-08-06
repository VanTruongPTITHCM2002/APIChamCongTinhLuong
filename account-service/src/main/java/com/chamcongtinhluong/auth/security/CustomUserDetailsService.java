package com.chamcongtinhluong.auth.security;

import com.chamcongtinhluong.auth.entity.Account;
import com.chamcongtinhluong.auth.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Account account = accountRepository.findByUsername(username).orElse(null);
        if (account.getUsername().equals(username)) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(account.getUsername())// password "password" đã mã hóa với BCrypt
                    .password(account.getPassword())
                    .authorities(account.getRoles().getRolename())
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}