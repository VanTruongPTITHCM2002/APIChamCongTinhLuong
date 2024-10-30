package com.chamcongtinhluong.account_service.controller;

import com.chamcongtinhluong.account_service.dto.request.PermissonsRequest;
import com.chamcongtinhluong.account_service.service.PermissonsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/users/{username}")
    private ResponseEntity<?> addPermissonsForUser(@PathVariable String username,
                                                   @RequestBody List<String> permissons){
        return permissonsService.addPermissonsUsers(username,permissons);
    }

    @PutMapping
    private ResponseEntity<?> updatePermissons(@RequestParam String permissons,
            @Validated
            @RequestBody PermissonsRequest permissonsRequest
    ){
        return permissonsService.updatePermissons(permissons,permissonsRequest);
    }

    @PutMapping("/users/{username}")
    private ResponseEntity<?>updatePermissonsForUser(@PathVariable String username,
                                                     @RequestBody List<String> permissons){
        return permissonsService.updatePermissonsUsers(username,permissons);
    }

    @DeleteMapping
    private ResponseEntity<?> deletePermissons(@RequestParam String permissons){
        return permissonsService.deletePermissons(permissons);
    }
}
