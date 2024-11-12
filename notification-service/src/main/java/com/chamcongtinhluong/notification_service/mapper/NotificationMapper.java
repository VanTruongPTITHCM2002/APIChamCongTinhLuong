package com.chamcongtinhluong.notification_service.mapper;

import com.chamcongtinhluong.notification_service.dto.request.NotificationRequest;
import com.chamcongtinhluong.notification_service.dto.response.NotificationResponse;
import com.chamcongtinhluong.notification_service.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);


    @Mapping(source = "senderId", target = "senderId")
    @Mapping(source = "receiverId", target = "receiverId")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createAt",target = "createAt")
    @Mapping(source = "updateAt", target = "updateAt")
   Notification toEntity(NotificationRequest notificationRequest);


    @Mapping(source = "senderId", target = "senderId")
    @Mapping(source = "receiverId", target = "receiverId")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createAt",target = "createAt")
    @Mapping(source = "updateAt", target = "updateAt")
    NotificationResponse toResponse(Notification notification);
}
