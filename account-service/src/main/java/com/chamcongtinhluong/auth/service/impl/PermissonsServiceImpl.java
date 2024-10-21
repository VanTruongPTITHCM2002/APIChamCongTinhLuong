package com.chamcongtinhluong.auth.service.impl;

import com.chamcongtinhluong.auth.dto.request.PermissonsRequest;
import com.chamcongtinhluong.auth.dto.response.PermissonsResponse;
import com.chamcongtinhluong.auth.dto.response.ResponseObject;
import com.chamcongtinhluong.auth.entity.Permissons;
import com.chamcongtinhluong.auth.repository.PermissonsRepository;
import com.chamcongtinhluong.auth.service.PermissonsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PermissonsServiceImpl implements PermissonsService {

    private final PermissonsRepository permissonsRepository;

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

}
