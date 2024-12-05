package com.chamcongtinhluong.ai_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaceRecognizeRequest {
    private String idEmployee;
    private Integer[] faceDescriptor;
}
