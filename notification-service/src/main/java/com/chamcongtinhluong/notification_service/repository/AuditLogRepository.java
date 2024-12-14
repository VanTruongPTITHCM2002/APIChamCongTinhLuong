package com.chamcongtinhluong.notification_service.repository;

import com.chamcongtinhluong.notification_service.dto.response.AccessResponse;
import com.chamcongtinhluong.notification_service.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog,Integer> {
    @Query("SELECT new com.chamcongtinhluong.notification_service.dto.response.AccessResponse(" +
            "COUNT(a), DATE(a.createtime)) " +
            "FROM AuditLog a " +
            "WHERE a.action = :action " +
            "GROUP BY DATE(a.createtime)")
    List<AccessResponse> findNumberAccess(@Param("action") String action);
}
