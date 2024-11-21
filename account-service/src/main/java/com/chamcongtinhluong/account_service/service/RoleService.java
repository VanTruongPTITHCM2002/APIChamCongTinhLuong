package com.chamcongtinhluong.account_service.service;

import com.chamcongtinhluong.account_service.dto.request.RoleRequest;
import org.springframework.http.ResponseEntity;

public interface RoleService {
    ResponseEntity<?>getRoles();
    ResponseEntity<?>addRole(RoleRequest roleRequest);
    ResponseEntity<?>updateRole(String roleName,RoleRequest roleRequest);
    ResponseEntity<?>deleteRole(String roleName);
}
