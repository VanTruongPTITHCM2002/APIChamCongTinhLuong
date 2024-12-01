package com.chamcongtinhluong.notification_service.service;

import com.chamcongtinhluong.notification_service.dto.request.NotificationRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NotificationService {
    ResponseEntity<?> getNotifications();
    ResponseEntity<?> addNotification(NotificationRequest notificationRequest);
    ResponseEntity<?> deleteNotification(NotificationRequest notificationRequestList);
}
