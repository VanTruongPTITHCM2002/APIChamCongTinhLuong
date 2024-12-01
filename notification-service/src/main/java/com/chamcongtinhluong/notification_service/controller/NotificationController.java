package com.chamcongtinhluong.notification_service.controller;

import com.chamcongtinhluong.notification_service.dto.request.NotificationRequest;
import com.chamcongtinhluong.notification_service.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    private ResponseEntity<?> getNotifications(){
        return notificationService.getNotifications();
    }

    @PostMapping
    private ResponseEntity<?> addNotification(@RequestBody @Validated NotificationRequest notificationRequest){
        return notificationService.addNotification(notificationRequest);
    }

    @DeleteMapping
    private ResponseEntity<?> deleteNotification(@RequestBody @Validated NotificationRequest notificationRequest){
        return notificationService.deleteNotification(notificationRequest);
    }
}
