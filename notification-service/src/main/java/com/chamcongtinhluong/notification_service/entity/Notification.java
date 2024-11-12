package com.chamcongtinhluong.notification_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificationId")
    private int notificationId;

    @Column(name = "senderId",length = 45)
    private String senderId;

    @Column(name = "receiverId", length = 45)
    private String receiverId;

    @Column(name = "content")
    private String content;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private String status;

    @Column(name = "createAt")
    private Date createAt;

    @Column(name = "updateAt")
    private Date updateAt;

}
