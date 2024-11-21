package com.chamcongtinhluong.account_service.controller;

import com.chamcongtinhluong.account_service.dto.request.RoleRequest;
import com.chamcongtinhluong.account_service.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    private ResponseEntity<?> getRoles(){
        return roleService.getRoles();
    }

    @PostMapping
    private ResponseEntity<?> addRole(@Validated @RequestBody RoleRequest roleRequest){
        return roleService.addRole(roleRequest);
    }

    @PutMapping
    private ResponseEntity<?> updateRole(@Validated @RequestBody RoleRequest roleRequest,@RequestParam String roleName){
        return roleService.updateRole(roleName,roleRequest);
    }

    @DeleteMapping
    private ResponseEntity<?> deleteRole(@RequestParam String roleName){
        return roleService.deleteRole(roleName);
    }
}
