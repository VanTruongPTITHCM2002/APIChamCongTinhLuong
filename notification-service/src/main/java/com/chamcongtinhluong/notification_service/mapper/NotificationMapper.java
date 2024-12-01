package com.chamcongtinhluong.notification_service.mapper;

import com.chamcongtinhluong.notification_service.dto.request.NotificationRequest;
import com.chamcongtinhluong.notification_service.dto.response.NotificationResponse;
import com.chamcongtinhluong.notification_service.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.text.SimpleDateFormat;
import java.util.Date;


@Mapper
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);


    @Mapping(source = "senderId", target = "senderId")
    @Mapping(source = "receiverId", target = "receiverId")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createAt",target = "createAt",qualifiedByName = "stringToDate")
    @Mapping(source = "updateAt", target = "updateAt",qualifiedByName = "stringToDate")
   Notification toEntity(NotificationRequest notificationRequest);


    @Mapping(source = "senderId", target = "senderId")
    @Mapping(source = "receiverId", target = "receiverId")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createAt",target = "createAt",qualifiedByName = "dateToString")
    @Mapping(source = "updateAt", target = "updateAt",qualifiedByName = "dateToString")
    NotificationResponse toResponse(Notification notification);

    @Named("dateToString")
    default String dateToString(Date date) {
        if (date == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(date);
    }

    @Named("stringToDate")
    default Date stringToDate(String dateStr) {
        if (dateStr == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            return formatter.parse(dateStr);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
