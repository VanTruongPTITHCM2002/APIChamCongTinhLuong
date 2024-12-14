package com.chamcongtinhluong.notification_service.service;

import com.chamcongtinhluong.notification_service.dto.request.AuditLogRequest;
import com.chamcongtinhluong.notification_service.dto.response.AccessResponse;
import com.chamcongtinhluong.notification_service.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuditLogService {
    ResponseEntity<ApiResponse> getAuditLog();
    ResponseEntity<ApiResponse> addAuditLog(AuditLogRequest auditLogRequest);
    List<AccessResponse> getAccessWeb(String action);
}
