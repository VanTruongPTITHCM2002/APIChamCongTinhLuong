package com.chamcongtinhluong.auth.service.impl;

import com.chamcongtinhluong.auth.dto.AccountDTO;
import com.chamcongtinhluong.auth.dto.AccountRequest;
import com.chamcongtinhluong.auth.dto.AccountResponse;
import com.chamcongtinhluong.auth.dto.ChangePasswordRequest;
import com.chamcongtinhluong.auth.entity.Account;
import com.chamcongtinhluong.auth.entity.CreateAccountRequest;
import com.chamcongtinhluong.auth.entity.ResponseObject;
import com.chamcongtinhluong.auth.entity.Role;
import com.chamcongtinhluong.auth.filter.JwtService;
import com.chamcongtinhluong.auth.repository.AccountRepository;
import com.chamcongtinhluong.auth.repository.RoleRepository;
import com.chamcongtinhluong.auth.service.AccountService;
import com.chamcongtinhluong.auth.utils.ConvertStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ConvertStatus convertStatus;

    @Autowired
    private JwtService jwtService;

    @Override
    public ResponseEntity<?> getAccounts() {
        List<AccountResponse> lst = new ArrayList<>();
        for(Account acc: accountRepository.findAll().stream().filter(e -> !e.getRoles().getRolename().equals("ADMIN")).toList()){
           AccountResponse accountResponse = new AccountResponse();
           accountResponse.setUsername(acc.getUsername());
           accountResponse.setRole(acc.getRoles().getRolename().equals("USER") ?"Nhân viên": "Quản lý");
           accountResponse.setStatus(convertStatus.convert(acc.getStatus()));
            lst.add(accountResponse);
        }
        return ResponseEntity.ok().body(new ResponseObject(HttpStatus.OK.value(),"Get Account successfully",lst));
    }

    @Override
    public ResponseEntity<?> getAccountByUsername(AccountRequest accountRequest) {
        Account account = accountRepository.findByUsername(accountRequest.getUsername()).orElse(null);
        if(account == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(HttpStatus.NOT_FOUND.value(),"Không tìm thấy tài khoản",null));
        }
        if(!passwordEncoder.matches(accountRequest.getPassword(),account.getPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.value(),"Tài khoản hoặc mật khẩu không đúng",null));
        }
        AccountDTO accountDTO = new AccountDTO();
        //  acc.setPassword(passwordEncoder.encode(acc.getPassword()));

        accountDTO.setUsername(account.getUsername());
        accountDTO.setToken(jwtService.generateToken(account.getUsername(),account.getRoles().getRolename()));
        accountDTO.setRole(account.getRoles().getRolename());
        accountDTO.setStatus(account.getStatus());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(),"Đăng nhập thành công",accountDTO));
    }

    @Override
    public String addAccountFromClient(CreateAccountRequest createAccountRequest){
        Role role = roleRepository.findById(createAccountRequest.getRole()).orElse(null);
        Account account = new Account();
        account.setUsername(createAccountRequest.getIdemployee());
        account.setPassword(passwordEncoder.encode(createAccountRequest.getPassword()));
        account.setRoles(role);
        account.setCreate_at(new Date());
        accountRepository.save(account);
        return "Created account success";
    }

    @Override
    public ResponseEntity<?> updateAccount(String username, AccountResponse accountResponse) {
        Account account = accountRepository.findByUsername(accountResponse.getUsername()).orElse(null);
        account.setStatus(convertStatus.convert(accountResponse.getStatus()));
        accountRepository.save(account);
        return ResponseEntity.ok().body(new ResponseObject(HttpStatus.OK.value(), "Sửa trạng thái thành công",""));
    }

    @Override
    public ResponseEntity<?> changeStatusAccount(String username) {
        Account account = accountRepository.findByUsername(username).orElse(null);
        if(account == null){
            return ResponseEntity.ok().body(new ResponseObject(HttpStatus.NOT_FOUND.value(), "Không tìm thấy tài khoản",""));
        }
        account.setStatus(0);
        accountRepository.save(account);
        return ResponseEntity.ok().body(
                new ResponseObject(HttpStatus.OK.value(), "Chỉnh sửa trạng thái tài khoản thành công","")
        );
    }

    @Override
    public ResponseEntity<?> resetPassword(String username) {
        Account account = accountRepository.findByUsername(username).orElse(null);
        account.setPassword(passwordEncoder.encode("1"));
        accountRepository.save(account);
        return ResponseEntity.ok().body(new ResponseObject(HttpStatus.OK.value(), "Mật khẩu đã được đặt lại thành công",""));
    }

    @Override
    public ResponseEntity<?> changePassword(ChangePasswordRequest changePasswordRequest) {
        Account account = accountRepository.findByUsername(changePasswordRequest.getUsername()).orElse(null);
        if(account == null){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ResponseObject.builder().status(HttpStatus.NOT_FOUND.value())
                            .message("Không tìm thấy tài khoản")
                            .data("")
                            .build()
            );
        }
        if(!passwordEncoder.matches(changePasswordRequest.getOldpassword(),account.getPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.value(),"Tài khoản hoặc mật khẩu không đúng",null));
        }
        account.setPassword(passwordEncoder.encode(changePasswordRequest.getNewpassword()));
        accountRepository.save(account);
        return ResponseEntity.ok()
                .body(ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message("Thay đổi mật khẩu thành công")
                        .data("")
                        .build());
    }

    @Override
    public ResponseEntity<?> loginHomePage(AccountRequest accountRequest) {
        Account account = accountRepository.findByUsername(accountRequest.getUsername()).orElse(null);
        if(account == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(HttpStatus.NOT_FOUND.value(),"Không tìm thấy tài khoản",null));
        }
        if(!passwordEncoder.matches(accountRequest.getPassword(),account.getPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.value(),"Tài khoản hoặc mật khẩu không đúng",null));
        }
        Map<String,String> map = new HashMap();
        String token = jwtService.generateToken(account.getUsername(),account.getRoles().getRolename());
        map.put("username",accountRequest.getUsername());
        map.put("token",token);
        return ResponseEntity.ok().body(
                ResponseObject.builder().status(HttpStatus.OK.value())
                        .message("Đăng nhập thành công")
                        .data(map).build()
        );
    }

    @Override
    public String changStatusAccount(String idemployee) {
        Account account = accountRepository.findByUsername(idemployee).orElse(null);
        if(account == null){
            return "Thất bại";
        }
        account.setStatus(0);
        accountRepository.save(account);
        return "Thành công";
    }


}
