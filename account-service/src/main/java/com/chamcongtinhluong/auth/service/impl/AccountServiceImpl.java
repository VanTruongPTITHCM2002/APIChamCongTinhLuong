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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ConvertStatus convertStatus;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<?> getAccounts() {
        try{
            List<AccountResponse> listAccount = roleRepository.findByRole("USER").stream()
                    .map(account ->
                            AccountResponse.builder()
                                    .username(account.getUsername())
                                    .role("Nhân viên")
                                    .status(convertStatus.convert(account.getStatus()))
                                    .build()
                    )
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(
                    ResponseObject.builder()
                            .status(HttpStatus.OK.value())
                            .message("Lấy danh sách tài khoản thành công")
                            .data(listAccount)
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            ResponseObject.builder()
                                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                    .message("Lỗi kết nối đến server " + e.getMessage())
                                    .build()
                    );
        }

    }

    @Override
    public ResponseEntity<?> getAccountByUsername(AccountRequest accountRequest) {
        try{
            Account account = accountRepository.findByUsername(accountRequest.getUsername()).orElse(null);

            if(!passwordEncoder.matches(accountRequest.getPassword(),account.getPassword())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        ResponseObject.builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message("Tài khoản hoặc mật khẩu không đúng")
                                .build()
                );
            }


            return ResponseEntity.status(HttpStatus.OK).body(
                    ResponseObject.builder()
                            .status(HttpStatus.OK.value())
                            .message("Đăng nhập thành công")
                            .data(AccountDTO.builder()
                                    .username(account.getUsername())
                                    .token(jwtService.generateToken(account.getUsername(),account.getRoles().getRolename()))
                                    .role(account.getRoles().getRolename())
                                    .status(account.getStatus())
                                    .build())
                            .build()
            );
        }catch (NullPointerException nullPointerException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ResponseObject.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("Không tìm thấy tài khoản " + accountRequest.getUsername())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            ResponseObject.builder()
                                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                    .message("Kết nối tới server có lỗi " + e.getMessage())
                                    .build()
                    );
        }

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
        log.info("Tài Khoản đã được tạo");
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ResponseObject.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("Không tìm thấy tài khoản " + accountRequest.getUsername())
                            .build()
            );
        }
        if(!passwordEncoder.matches(accountRequest.getPassword(),account.getPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ResponseObject.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Tài khoản hoặc mật khẩu không đúng")
                            .build()
            );
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

    @Override
    public ResponseEntity<?> deleteAccount(String username) {
        Account account = accountRepository.findByUsername(username).orElse(null);
        accountRepository.delete(account);
        return ResponseEntity.ok()
                .body(ResponseObject.builder().status(HttpStatus.OK.value())
                        .message("Xóa tài khoản nhân viên thành công").data("").build());
    }

}
