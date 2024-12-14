package com.chamcongtinhluong.notification_service.service.impl;

import com.chamcongtinhluong.notification_service.dto.request.AuditLogRequest;
import com.chamcongtinhluong.notification_service.dto.response.AccessResponse;
import com.chamcongtinhluong.notification_service.dto.response.ApiResponse;
import com.chamcongtinhluong.notification_service.entity.AuditLog;
import com.chamcongtinhluong.notification_service.repository.AuditLogRepository;
import com.chamcongtinhluong.notification_service.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public ResponseEntity<ApiResponse> getAuditLog() {
        List<AuditLogRequest> auditLogRequestList = auditLogRepository.findAll()
                .stream().map(auditLog ->
                        AuditLogRequest.builder()
                                .username(auditLog.getUsername())
                                .action(auditLog.getAction())
                                .description(auditLog.getDescription())
                                .createtime(auditLog.getCreatetime())
                                .build()
                ).toList();
        return ResponseEntity.ok().body(ApiResponse
                .builder()
                        .status(HttpStatus.OK.value())
                        .message("Thành công")
                        .data(auditLogRequestList)
                .build());
    }

    @Override
    public ResponseEntity<ApiResponse> addAuditLog(AuditLogRequest auditLogRequest) {
        AuditLog auditLog = new AuditLog();
        auditLog.setUsername(auditLogRequest.getUsername());
        auditLog.setAction(auditLogRequest.getAction());
        auditLog.setDescription(auditLogRequest.getDescription());
        auditLog.setCreatetime(auditLogRequest.getCreatetime());
        auditLogRepository.save(auditLog);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse
                .builder()
                        .status(HttpStatus.CREATED.value())
                .build());
    }

    @Override
    public List<AccessResponse> getAccessWeb(String action) {
        List<AccessResponse> accessResponses = auditLogRepository.findNumberAccess(action);
        if(accessResponses.isEmpty()){
            return null;
        }
        return accessResponses;
    }
}
