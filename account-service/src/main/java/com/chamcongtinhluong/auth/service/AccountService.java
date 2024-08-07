package com.chamcongtinhluong.auth.service;

import com.chamcongtinhluong.auth.dto.AccountRequest;
import com.chamcongtinhluong.auth.dto.AccountResponse;
import com.chamcongtinhluong.auth.dto.ChangePasswordRequest;
import com.chamcongtinhluong.auth.entity.CreateAccountRequest;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    ResponseEntity<?>getAccounts();
    ResponseEntity<?>getAccountByUsername(AccountRequest accountRequest);
    String addAccountFromClient(CreateAccountRequest createAccountRequest);
    ResponseEntity<?>updateAccount(String username, AccountResponse accountResponse);
    ResponseEntity<?>changeStatusAccount(String username);
    ResponseEntity<?>resetPassword(String username);
    ResponseEntity<?>changePassword(ChangePasswordRequest changePasswordRequest);
    ResponseEntity<?>loginHomePage(AccountRequest accountRequest);
    String changStatusAccount(String idemployee);
    ResponseEntity<?> deleteAccount(String username);
}
