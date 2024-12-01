package com.chamcongtinhluong.notification_service.repository;

import com.chamcongtinhluong.notification_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    @Query("SELECT n FROM Notification n WHERE n.type = :type AND n.content = :content " +
            "AND n.senderId = :senderId AND n.receiverId = :receiverId AND DATE_FORMAT(n.createAt, '%d/%m/%Y %H:%i:%s') = :createAt")
    Notification findByTypeContentSenderIdReceiverIdAndCreateAt(String type, String content,
                                                                      String senderId, String receiverId,
                                                                    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") Date createAt);
}
