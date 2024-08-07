package com.chamcongtinhluong.auth.controller;

import com.chamcongtinhluong.auth.dto.AccountRequest;
import com.chamcongtinhluong.auth.dto.AccountResponse;
import com.chamcongtinhluong.auth.dto.ChangePasswordRequest;
import com.chamcongtinhluong.auth.entity.CreateAccountRequest;
import com.chamcongtinhluong.auth.service.AccountService;
import com.chamcongtinhluong.auth.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController

@RequestMapping("api/v1")
public class AuthController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailService emailService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/token")
    public ResponseEntity<?> getAccess(){
        return ResponseEntity.ok().body("You allow");
    }

    @GetMapping("/account")
    public ResponseEntity<?> getAccounts(){
       return accountService.getAccounts();
    }

    @PutMapping("/account/{username}")
    public ResponseEntity<?> updateAccount(@PathVariable String username, @RequestBody AccountResponse accountResponse){
        return accountService.updateAccount(username,accountResponse);
    }

    @PutMapping("/account/change_password")
    public  ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        return accountService.changePassword(changePasswordRequest);
    }


    @PutMapping("/account/{username}/reset_password")
    public ResponseEntity<?> resetPassword(@PathVariable String username){
        return accountService.resetPassword(username);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> getAccountByUsername(@RequestBody AccountRequest accountRequest){
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
    @GetMapping("/send-email")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String text) {
        emailService.sendSimpleMessage(to, subject, text);
        return "Email sent!";
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
