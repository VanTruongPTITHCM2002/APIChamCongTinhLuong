package com.chamcongtinhluong.notification_service.service.impl;

import com.chamcongtinhluong.notification_service.dto.request.NotificationRequest;
import com.chamcongtinhluong.notification_service.dto.response.ApiResponse;
import com.chamcongtinhluong.notification_service.dto.response.NotificationResponse;
import com.chamcongtinhluong.notification_service.entity.Notification;
import com.chamcongtinhluong.notification_service.mapper.NotificationMapper;
import com.chamcongtinhluong.notification_service.repository.NotificationRepository;
import com.chamcongtinhluong.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private  final NotificationRepository notificationRepository;


    @Override
    public ResponseEntity<?> getNotifications() {
        List<NotificationResponse> notificationResponseList = notificationRepository
                .findAll().stream().map(NotificationMapper.INSTANCE::toResponse
                ).toList();
        return ResponseEntity.ok().body(
                ApiResponse.builder().
                 status(HttpStatus.OK.value())
                        .message("Lay thanh cong danh sach tin nhan")
                        .data(notificationResponseList)
                .build()
        );
    }

    @Override
    public ResponseEntity<?> addNotification(NotificationRequest notificationRequest) {
        try{
            Notification notification = NotificationMapper.INSTANCE.toEntity(notificationRequest);
            notification.setCreateAt(new Date());
            notificationRepository.save(notification);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Loi ket noi co so du lieu khong them thong bao")
                            .build());
        }
        return ResponseEntity.status(
                HttpStatus.CREATED
        ).body(ApiResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Them tin nhan thanh cong")
                .build());
    }

    @Override
    public ResponseEntity<?> deleteNotification(NotificationRequest notificationRequestList) {
        try{

           Notification notification = notificationRepository.findByTypeContentSenderIdReceiverIdAndCreateAt(
                   notificationRequestList.getType(),notificationRequestList.getContent(),
                   notificationRequestList.getSenderId(),notificationRequestList.getReceiverId(),
                  notificationRequestList.getCreateAt()
           );
           if(notification == null){
               return ResponseEntity.status(HttpStatus.NOT_FOUND)
                       .body(ApiResponse.builder()
                               .status(HttpStatus.NOT_FOUND.value())
                               .message("Không tìm thấy thông báo")
                               .build());
           }
           notificationRepository.delete(notification);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.builder()
                            .status(HttpStatus.OK.value())
                            .message("Xóa thông báo thành công")
                            .build());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Loi ket noi co so du lieu khong xoa thong bao")
                            .build());
        }
    }
}
