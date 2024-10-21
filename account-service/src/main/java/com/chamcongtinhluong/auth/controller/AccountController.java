package com.chamcongtinhluong.auth.controller;

import com.chamcongtinhluong.auth.dto.response.AccountResponse;
import com.chamcongtinhluong.auth.dto.request.ChangePasswordRequest;
import com.chamcongtinhluong.auth.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<?> getAccounts(){
        return accountService.getAccounts();
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateAccount(@PathVariable String username,
                                           @RequestBody AccountResponse accountResponse){
        return accountService.updateAccount(username,accountResponse);
    }

    @PutMapping("/change_password")
    public  ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        return accountService.changePassword(changePasswordRequest);
    }

    @PutMapping("/{username}/reset_password")
    public ResponseEntity<?> resetPassword(@PathVariable String username){
        return accountService.resetPassword(username);
    }

    @PutMapping("/changestatus/{idemployee}")
    public String changeStatus(@PathVariable String idemployee){
        return accountService.changStatusAccount(idemployee);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteAccount(@PathVariable String username){
        return accountService.deleteAccount(username);
    }
}
