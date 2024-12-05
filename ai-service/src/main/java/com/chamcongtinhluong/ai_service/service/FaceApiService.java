package com.chamcongtinhluong.ai_service.service;

import com.chamcongtinhluong.ai_service.dto.request.FaceApiRequest;
import com.chamcongtinhluong.ai_service.dto.request.FaceRecognizeRequest;
import com.chamcongtinhluong.ai_service.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface FaceApiService {
    ResponseEntity<ApiResponse> saveFaceApi(FaceApiRequest faceApiRequest);
    ResponseEntity<ApiResponse> recognizeFaceApi(FaceRecognizeRequest faceRecognizeRequest);
}
