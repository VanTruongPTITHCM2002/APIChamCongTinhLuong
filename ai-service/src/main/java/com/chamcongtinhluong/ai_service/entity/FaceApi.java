package com.chamcongtinhluong.ai_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "facedetect")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaceApi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idfacedetect;

    @Column(name = "idemployee",length = 45)
    private String idEmployee;

    @Lob // Sử dụng @Lob để đánh dấu trường này là kiểu Large Object (BLOB hoặc CLOB)
    @Column(name = "face_descriptor", columnDefinition = "MEDIUMBLOB", nullable = false)
    private byte[] faceDescriptor;
}
