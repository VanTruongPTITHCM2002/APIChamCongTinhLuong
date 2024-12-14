package com.chamcongtinhluong.notification_service.controller;

import com.chamcongtinhluong.notification_service.dto.request.AuditLogRequest;
import com.chamcongtinhluong.notification_service.dto.response.AccessResponse;
import com.chamcongtinhluong.notification_service.dto.response.ApiResponse;
import com.chamcongtinhluong.notification_service.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audit-log")
@RequiredArgsConstructor
public class AuditLogController {
    private final AuditLogService auditLogService;

    @GetMapping
    private ResponseEntity<ApiResponse> getAuditLog(){
        return auditLogService.getAuditLog();
    }

    @GetMapping("/count")
    private List<AccessResponse> getAccess(@RequestParam String action){
        return auditLogService.getAccessWeb(action);
    }

    @PostMapping
    private ResponseEntity<ApiResponse> addAuditLog(@RequestBody AuditLogRequest auditLogRequest){
        return auditLogService.addAuditLog(auditLogRequest);
    }
}
