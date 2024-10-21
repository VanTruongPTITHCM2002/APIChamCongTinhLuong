package com.chamcongtinhluong.auth.service;

import com.chamcongtinhluong.auth.dto.request.PermissonsRequest;
import org.springframework.http.ResponseEntity;

public interface PermissonsService {
    ResponseEntity<?>getAllPermissons();
    ResponseEntity<?>addPermissons(PermissonsRequest permissonsRequest);
}
