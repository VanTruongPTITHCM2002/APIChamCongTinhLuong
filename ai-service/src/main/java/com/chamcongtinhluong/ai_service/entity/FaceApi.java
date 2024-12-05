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

    @Lob
    @Column(name = "face_descriptor",nullable = false)
    private byte[] faceDescriptor;
}
