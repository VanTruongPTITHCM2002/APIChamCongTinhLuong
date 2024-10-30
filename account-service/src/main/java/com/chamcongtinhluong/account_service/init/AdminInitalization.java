package com.chamcongtinhluong.account_service.init;

import com.chamcongtinhluong.account_service.entity.Account;
import com.chamcongtinhluong.account_service.entity.Role;
import com.chamcongtinhluong.account_service.repository.AccountRepository;
import com.chamcongtinhluong.account_service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitalization {
    private final PasswordEncoder passwordEncoder;
    private final static Logger log = LoggerFactory.getLogger(AdminInitalization.class);

    @Bean
    ApplicationRunner init(AccountRepository accountRepository, RoleRepository roleRepository){
        return args -> {
            if(accountRepository.findAll()
                    .stream().filter(
                            account -> account.getRoles().getRolename().equals("ADMIN")
                    )
                    .toList().isEmpty()){
                Role role = roleRepository.findByRolename("ADMIN");
                Account account = new Account();
                account.setUsername("admin");
                account.setPassword(passwordEncoder.encode("1"));
                account.setRoles(role);
                accountRepository.save(account);
                log.info("Tai khoan quan tri da duoc tao");
            }
        };
    }
}
