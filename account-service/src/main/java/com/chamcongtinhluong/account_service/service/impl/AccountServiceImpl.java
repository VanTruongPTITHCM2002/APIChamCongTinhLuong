package com.chamcongtinhluong.account_service.service.impl;


import com.chamcongtinhluong.account_service.dto.response.*;
import com.chamcongtinhluong.account_service.dto.request.AccountRequest;
import com.chamcongtinhluong.account_service.dto.request.ChangePasswordRequest;
import com.chamcongtinhluong.account_service.entity.Account;
import com.chamcongtinhluong.account_service.dto.request.CreateAccountRequest;
import com.chamcongtinhluong.account_service.entity.Role;
import com.chamcongtinhluong.account_service.filter.JwtService;
import com.chamcongtinhluong.account_service.repository.AccountRepository;
import com.chamcongtinhluong.account_service.repository.RolePermissonsRepository;
import com.chamcongtinhluong.account_service.repository.RoleRepository;
import com.chamcongtinhluong.account_service.service.AccountService;
import com.chamcongtinhluong.account_service.utils.ConvertStatus;
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
    private final RolePermissonsRepository rolePermissonsRepository;
    private final ConvertStatus convertStatus;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<?> getAccounts() {
        try{
            List<AccountResponse> listAccount = accountRepository.findAll().stream().filter(acc->!acc.getRoles().getRolename().equals("ADMIN"))
                    .map(account ->
                            AccountResponse.builder()
                                    .username(account.getUsername())
                                    .role(account.getRoles().getRoleDescription())
                                    .status(convertStatus.convert(account.getStatus()))
                                    .build()
                    )
                    .collect(Collectors.toList());
            return new ResponseSuccess(HttpStatus.OK,"Lay danh sach tai khoan thanh cong",listAccount);

        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(
//                            ResponseObject.builder()
//                                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                                    .message("Loi ket noi den co so du lieu...")
//                                    .build()
//                    );
            return new ResponseFailure(HttpStatus.INTERNAL_SERVER_ERROR,"Loi ket noi den co so du lieu...");
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
            List<String> permissions = rolePermissonsRepository.findByRolePermissonsID_Role(account.getRoles().getIdrole())
                    .stream().map(
                            rolePermissons -> rolePermissons.getPermissons().getNamepermisson()
                    ).toList();

            return ResponseEntity.status(HttpStatus.OK).body(
                    ResponseObject.builder()
                            .status(HttpStatus.OK.value())
                            .message("Đăng nhập thành công")
                            .data(
                                    AccountLoginResponse.builder()
                                    .username(account.getUsername())
                                    .token(jwtService.generateToken(account.getUsername(),account.getRoles().getRolename(),permissions))
                                    .role(account.getRoles().getRolename())
                                    .roleDescription(account.getRoles().getRoleDescription())
                                    .status(account.getStatus())
                                    .build()
                            )
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
        try{
            Account account = accountRepository.findByUsername(accountResponse.getUsername()).orElse(null);
            account.setStatus(convertStatus.convert(accountResponse.getStatus()));
            if(!account.getRoles().getRoleDescription().equals(accountResponse.getRole())){
                Role role = roleRepository.findByRoleDescription(accountResponse.getRole());
                account.setRoles(role);
            }
            accountRepository.save(account);
        }catch (NullPointerException nullPointerException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseObject.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("Không tìm thấy tài khoản " + accountResponse.getUsername())
                            .build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            ResponseObject.builder()
                                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                    .message("Loi server khong the cap nhat..")
                                    .build()
                    );
        }
        return ResponseEntity.ok().body(ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message("Sửa tài khoản " + accountResponse.getUsername() + " thành công")
                .build());
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
            Map<String,String> map = new HashMap<String,String>();
            List<String> permissions = rolePermissonsRepository.findByRolePermissonsID_Role(account.getRoles().getIdrole())
                    .stream().map(
                            rolePermissons -> rolePermissons.getPermissons().getNamepermisson()
                                    ).toList();
            String token = jwtService.generateToken(account.getUsername(),account.getRoles().getRolename(),permissions);
            map.put("username",accountRequest.getUsername());
            map.put("token",token);
            return ResponseEntity.ok().body(
                    ResponseObject.builder().status(HttpStatus.OK.value())
                            .message("Đăng nhập thành công")
                            .data(map).build()
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
                                    .message("Khong the dang nhap do loi he thong")
                                    .build()
                    );
        }
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
