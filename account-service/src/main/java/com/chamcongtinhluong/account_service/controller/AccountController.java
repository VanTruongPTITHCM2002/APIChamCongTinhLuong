package com.chamcongtinhluong.account_service.controller;

import com.chamcongtinhluong.account_service.dto.response.AccountResponse;
import com.chamcongtinhluong.account_service.dto.request.ChangePasswordRequest;
import com.chamcongtinhluong.account_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/account")
    public ResponseEntity<?> getAccounts(){
        return accountService.getAccounts();
    }

    @GetMapping("/account/sum")
    public int getSumAccount(){
        return accountService.countSumAccount();
    }

    @GetMapping("/account/active")
    public int getAccountActive(){return accountService.countActiveAccount();}

    @GetMapping("/account/inactive")
    public int getAccountInActive(){return accountService.countInActiveAccount();}

    @PutMapping("/account/{username}")
    public ResponseEntity<?> updateAccount(@PathVariable String username,
                                           @RequestBody AccountResponse accountResponse){
        return accountService.updateAccount(username,accountResponse);
    }

    @PutMapping("public/account/change_password")
    public  ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        return accountService.changePassword(changePasswordRequest);
    }

    @PutMapping("/account/{username}/reset_password")
    public ResponseEntity<?> resetPassword(@PathVariable String username){
        return accountService.resetPassword(username);
    }

    @PutMapping("/account/changestatus/{idemployee}")
    public String changeStatus(@PathVariable String idemployee){
        return accountService.changStatusAccount(idemployee);
    }

    @DeleteMapping("/account/{username}")
    public ResponseEntity<?> deleteAccount(@PathVariable String username){
        return accountService.deleteAccount(username);
    }
}
