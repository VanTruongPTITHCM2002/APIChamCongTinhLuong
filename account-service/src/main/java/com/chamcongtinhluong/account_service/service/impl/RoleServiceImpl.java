package com.chamcongtinhluong.account_service.service.impl;

import com.chamcongtinhluong.account_service.dto.request.RoleRequest;
import com.chamcongtinhluong.account_service.dto.response.ResponseObject;
import com.chamcongtinhluong.account_service.dto.response.RoleResponse;
import com.chamcongtinhluong.account_service.entity.Role;
import com.chamcongtinhluong.account_service.mapper.RoleMapper;
import com.chamcongtinhluong.account_service.repository.RolePermissonsRepository;
import com.chamcongtinhluong.account_service.repository.RoleRepository;
import com.chamcongtinhluong.account_service.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RolePermissonsRepository rolePermissonsRepository;

    @Override
    public ResponseEntity<?> getRoles() {
        try{
            List<RoleResponse> roleResponseList= roleRepository.findAll().stream().map(
                    RoleMapper.INSTANCE::toResponse
            ).toList();
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .status(HttpStatus.OK.value())
                    .message("Lay thanh cong danh sach vai tro")
                    .data(roleResponseList)
                    .build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Loi ket noi den co so du lieu...")
                    .build());
        }

    }

    @Override
    public ResponseEntity<?> addRole(RoleRequest roleRequest) {
        if(roleRepository.findByRolename(roleRequest.getRolename()) !=  null){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseObject.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Ten vai tro da ton tai")
                            .build());
        }
        try{
            roleRequest.setCreateAt(new Date());
            Role role = RoleMapper.INSTANCE.toEntity(roleRequest);
            role.setIsActive(true);
            roleRepository.save(role);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseObject.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Loi ket noi den co so du lieu..")
                            .build());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseObject.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Them vai tro thanh cong")
                        .build());
    }

    @Override
    public ResponseEntity<?> updateRole(String roleName,RoleRequest roleRequest) {
        if(roleRepository.findByRolename(roleName) ==  null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ResponseObject.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("Khong tim thay vai tro co ten " + roleName)
                            .build()
            );
        }
        try{
            roleRequest.setUpdateAt(new Date());
             Role role =roleRepository.findByRolename(roleName);
             role.setRolename(roleRequest.getRolename());
             role.setRoleDescription(roleRequest.getRoleDescription());
             role.setCreateAt(roleRequest.getCreateAt());
             role.setUpdateAt(roleRequest.getUpdateAt());
             role.setScope(roleRequest.getScope());
             role.setIsActive(roleRequest.getIsActive());
            roleRepository.save(role);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder()
                            .status(HttpStatus.OK.value())
                            .message("Cap nhat vai tro thanh cong")
                    .build());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseObject.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Khong the ket noi den co so du lieu")
                            .build());
        }
    }

    @Override
    public ResponseEntity<?> deleteRole(String roleName) {
        if(roleName.equalsIgnoreCase("ADMIN")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ResponseObject.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Khong the thuc hien xoa vai tro nay")
                            .build()
            );
        }

        Role role = roleRepository.findByRolename(roleName);
        if(role == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseObject.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("Khong tim thay vai tro co ten " + roleName)
                            .build());
        }
        if(!rolePermissonsRepository.findByRolePermissonsID_Role(role.getIdrole()).isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ResponseObject.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Khong the thuc hien xoa vai tro nay")
                            .build()
            );
        }
        try{
            roleRepository.delete(role);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseObject.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Loi ket noi den co so du lieu")
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message("Xoa thanh cong vai tro")
                        .build()
        );
    }
}
