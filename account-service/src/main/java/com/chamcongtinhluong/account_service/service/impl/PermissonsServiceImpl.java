package com.chamcongtinhluong.account_service.service.impl;

import com.chamcongtinhluong.account_service.dto.request.PermissonsRequest;
import com.chamcongtinhluong.account_service.dto.response.*;
import com.chamcongtinhluong.account_service.entity.*;
import com.chamcongtinhluong.account_service.repository.AccountRepository;
import com.chamcongtinhluong.account_service.repository.PermissonsRepository;
import com.chamcongtinhluong.account_service.repository.RolePermissonsRepository;
import com.chamcongtinhluong.account_service.repository.RoleRepository;
import com.chamcongtinhluong.account_service.service.PermissonsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class PermissonsServiceImpl implements PermissonsService {

    private final PermissonsRepository permissonsRepository;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final RolePermissonsRepository rolePermissonsRepository;

    @Override
    public ResponseEntity<?> getAllPermissons() {
        try{
            List<PermissonsResponse> permissonsResponseList = permissonsRepository
                    .findAll()
                    .stream()
                    .map(permissons -> PermissonsResponse.builder()
                            .namepermisson(permissons.getNamepermisson())
                            .description(permissons.getDescription())
                            .build())
                    .toList();
            return ResponseEntity.ok().body(
                    ResponseObject.builder()
                            .status(HttpStatus.OK.value())
                            .message("Lay du lieu thanh cong")
                            .data(permissonsResponseList)
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseObject.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Server xay ra su co khong the lay du lieu..")
                            .build());
        }

    }

    @Override
    public ResponseEntity<?> getRolePermissons() {
        try{
            List<RolePermissonsResponse> rolePermissonsResponseList = roleRepository.findAll().stream()
                    .map(role -> RolePermissonsResponse.builder()
                            .roleName(role.getRolename())
                            .permissonsName(rolePermissonsRepository.findByRolePermissonsID_Role(role.getIdrole()).stream()
                                    .map(rolePermission -> rolePermission.getPermissons().getNamepermisson())
                                    .distinct()
                                    .collect(Collectors.toList()))
                            .build())
                    .collect(Collectors.toList());
            return new ResponseSuccess(HttpStatus.OK,"Lay danh sach thanh cong",rolePermissonsResponseList);
        }catch (Exception e){
            return new ResponseFailure(HttpStatus.INTERNAL_SERVER_ERROR,"Loi ket noi den co so du lieu...");
        }

    }

    @Override
    public ResponseEntity<?> addPermissons(PermissonsRequest permissonsRequest) {
        Boolean isCheckNamePermissons = permissonsRepository.existsByNamepermisson(permissonsRequest.getNamePermissons());
        if(isCheckNamePermissons){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseObject.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Ten quyen da ton tai")
                            .build());
        }
        try{
            permissonsRepository.save(Permissons.builder()
                            .namepermisson(permissonsRequest.getNamePermissons())
                            .description(permissonsRequest.getDescription())
                            .createAt(new Date())
                            .updateAt(permissonsRequest.getUpdateAt())
                    .build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseObject.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Server xay ra loi khong the them..")
                            .build());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseObject.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Them du lieu thanh cong")
                        .build());
    }

    @Override
    public ResponseEntity<?> addPermissonsUsers(String username, List<String> permissons) {
        try{
            if(username.isEmpty() || permissons.isEmpty()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tai khoan hoặc Permission không hợp lệ");
            }
            Account account = accountRepository.findByUsername(username).orElse(null);
            Role role = roleRepository.findByRolename(account.getRoles().getRolename());

            for(String perm: permissons){
                rolePermissonsRepository.save(
                        RolePermissons.builder()
                                .rolePermissonsID(RolePermissonsID.builder()
                                        .role(role.getIdrole())
                                        .permissons(permissonsRepository.findByNamepermisson(perm).getIdpermissons())
                                        .build())
                                .role(role)
                                .permissons(permissonsRepository.findByNamepermisson(perm))
                                .build()
                );
            }

        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseObject.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("Khong tim thay tai khoan " + username)
                            .build());
        }catch(Exception e){
            log.info(e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseObject.builder()
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message("Khong the ket noi den co so du lieu")
                                .build());
        }
        return ResponseEntity.status(
                HttpStatus.CREATED
        ).body(
                ResponseObject.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Them thanh cong")
                        .build()
        );
    }

    @Override
    public ResponseEntity<?> updatePermissons(String permissons, PermissonsRequest permissonsRequest) {
        Boolean isExistsPermissonName = permissonsRepository.existsByNamepermisson(permissonsRequest.getNamePermissons());
        if(isExistsPermissonName && !permissons.equals(permissonsRequest.getNamePermissons())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseObject.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Ten quyen da ton tai")
                            .build());
        }
        try{
            Permissons perm = permissonsRepository.findByNamepermisson(permissons);
            perm.setNamepermisson(permissonsRequest.getNamePermissons());
            perm.setUpdateAt(new Date());
            perm.setDescription(permissonsRequest.getDescription());
            permissonsRepository.save(perm);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseObject.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Loi ket noi co so du lieu khong the cap nhat")
                            .build());
        }
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message("Cap nhat thanh cong quyen")
                        .build()
        );
    }

    @Override
    public ResponseEntity<?> updatePermissonsUsers(String username, List<String> permissons) {
        try{
            if(username.isEmpty() || permissons.isEmpty()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tai khoan hoặc Permission không hợp lệ");
            }
            Account account = accountRepository.findByUsername(username).orElse(null);
            Role role = roleRepository.findByRolename(account.getRoles().getRolename());
            for(String perm: permissons){
                if(permissonsRepository.findByNamepermisson(perm) == null){
                    return ResponseEntity.status(
                            HttpStatus.NOT_FOUND
                    ).body(
                            ResponseObject.builder()
                                    .status(HttpStatus.NOT_FOUND.value())
                                    .message("Khong tim thay")
                                    .build()
                    );
                }
                rolePermissonsRepository.save(
                        RolePermissons.builder()
                                .rolePermissonsID(RolePermissonsID.builder()
                                        .role(role.getIdrole())
                                        .permissons(permissonsRepository.findByNamepermisson(perm).getIdpermissons())
                                        .build())
                                .role(role)
                                .permissons(permissonsRepository.findByNamepermisson(perm))
                                .build()
                );
            }

        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseObject.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("Khong tim thay tai khoan " + username)
                            .build());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseObject.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Khong the ket noi den co so du lieu")
                            .build());
        }
        return ResponseEntity.status(
                HttpStatus.OK
        ).body(
                ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message("Cap nhat thanh cong")
                        .build()
        );
    }

    @Override
    public ResponseEntity<?> deletePermissons(String permissons) {
        try{
            Permissons perm = permissonsRepository.findByNamepermisson(permissons);
            List<RolePermissons> rolePermissonsList = rolePermissonsRepository.findByRolePermissonsID_Permissons(perm.getIdpermissons());
            if(!rolePermissonsList.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(
                                ResponseObject.builder()
                                        .status(HttpStatus.BAD_REQUEST.value())
                                        .message("Khong the xoa")
                                        .build()
                        );
            }
            permissonsRepository.delete(perm);
        }catch (NullPointerException nullPointerException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ResponseObject.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("Khong tim thay quyen")
                            .build()
            );
        }catch (Exception e){
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            ResponseObject.builder()
                                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                    .message("Loi khong the ket noi co so du lieu...")
                                    .build()
                    );
        }
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message("Xoa thanh cong")
                        .build()
        );
    }

    @Override
    public ResponseEntity<?> deletePermissonsUsers(String username,List<String>permissons) {
        try{
            if(accountRepository.findByUsername(username).isEmpty()){
                return new ResponseFailure(HttpStatus.NOT_FOUND,"Khong tim thay tai khoan " + username);
            }
            Role role = accountRepository.findByUsername(username).get().getRoles();
            for(String perm: permissons){
                Permissons permissonsSearch = permissonsRepository.findByNamepermisson(perm);
                Boolean isExistsRolePermissons = rolePermissonsRepository.existsByRolePermissonsID_RoleAndRolePermissonsID_Permissons(role.getIdrole(),permissonsSearch.getIdpermissons());
                if(!isExistsRolePermissons){
                    return new ResponseFailure(HttpStatus.NOT_FOUND,"That bai do khong ton tai");
                }
                rolePermissonsRepository.delete(
                        RolePermissons.builder()
                                .permissons(permissonsSearch)
                                .role(role)
                                .rolePermissonsID(
                                        RolePermissonsID.builder()
                                                .role(role.getIdrole())
                                                .permissons(permissonsSearch.getIdpermissons())
                                                .build()
                                )
                                .build()
                );
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(
              ResponseObject.builder()
                      .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                      .message("Khong the ket noi den co so du lieu...")
                      .build()
            );
        }
        return new ResponseSuccess(HttpStatus.OK,"Xoa thanh cong");
    }

}
