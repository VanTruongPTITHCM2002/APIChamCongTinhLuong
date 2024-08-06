package com.chamcongtinhluong.workschedule.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .sessionManagement(se -> se.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Tắt tính năng Session In Memory Management trong Spring Security
                .authorizeHttpRequests(a -> {
                    a.anyRequest().permitAll();
                }).build();// Đặt LazySecurityContextProviderFilter đứng trước SessionManagementFilter

    }

}
