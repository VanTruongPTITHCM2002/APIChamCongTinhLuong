package com.chamcongtinhluong.account_service.service;

import com.chamcongtinhluong.account_service.dto.request.PermissonsRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PermissonsService {
    ResponseEntity<?>getAllPermissons();
    ResponseEntity<?>addPermissons(PermissonsRequest permissonsRequest);
    ResponseEntity<?>addPermissonsUsers(String username, List<String> permissons);
    ResponseEntity<?>updatePermissons(String permissons,PermissonsRequest permissonsRequest);
    ResponseEntity<?>updatePermissonsUsers(String username,List<String> permissons);
    ResponseEntity<?>deletePermissons(String permissons);
    ResponseEntity<?>deletePermissonsUsers(String username,List<String>permissons);
}
