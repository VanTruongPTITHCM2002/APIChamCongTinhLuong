package com.chamcongtinhluong.auth.controller;

import com.chamcongtinhluong.auth.dto.request.AccountRequest;
import com.chamcongtinhluong.auth.dto.request.CreateAccountRequest;
import com.chamcongtinhluong.auth.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> getAccountByUsername(
           @Validated @RequestBody AccountRequest accountRequest){
        return accountService.getAccountByUsername(accountRequest);
    }

    @PostMapping("/auth/login-user")
    public ResponseEntity<?> loginPage(@RequestBody AccountRequest accountRequest){
        return accountService.loginHomePage(accountRequest);
    }

    @PostMapping("/create_account")
    public String createAccount(@RequestBody CreateAccountRequest request) {
        return accountService.addAccountFromClient(request);
    }

}
