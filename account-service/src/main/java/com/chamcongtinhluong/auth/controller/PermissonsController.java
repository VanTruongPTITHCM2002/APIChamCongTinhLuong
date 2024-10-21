package com.chamcongtinhluong.auth.controller;

import com.chamcongtinhluong.auth.dto.request.PermissonsRequest;
import com.chamcongtinhluong.auth.dto.response.PermissonsResponse;
import com.chamcongtinhluong.auth.service.PermissonsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/permissons")
@RequiredArgsConstructor
public class PermissonsController {

    private final PermissonsService permissonsService;

    @GetMapping
    private ResponseEntity<?> getPermissons(){
        return permissonsService.getAllPermissons();
    }

    @PostMapping
    private ResponseEntity<?> addPermissons(
            @Validated
            @RequestBody PermissonsRequest permissonsRequest
    ){
        return permissonsService.addPermissons(permissonsRequest);
    }
}
