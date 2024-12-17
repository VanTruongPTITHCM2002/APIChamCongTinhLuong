package com.chamcongtinhluong.ai_service.controller;

import com.chamcongtinhluong.ai_service.dto.request.FaceApiRequest;
import com.chamcongtinhluong.ai_service.dto.request.FaceRecognizeRequest;
import com.chamcongtinhluong.ai_service.dto.response.ApiResponse;
import com.chamcongtinhluong.ai_service.service.FaceApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/face")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FaceApiController {

    private final FaceApiService faceApiService;

    @PostMapping
    public ResponseEntity<ApiResponse> saveFaceApiFromClient(@RequestBody FaceApiRequest faceApiRequest){
        return faceApiService.saveFaceApi(faceApiRequest);
    }

    @PostMapping("/recognize")
    public ResponseEntity<ApiResponse> recognizeFaceApiFromClient(@RequestBody FaceRecognizeRequest faceRecognizeRequest){
        return faceApiService.recognizeFaceApi(faceRecognizeRequest);
    }

    @GetMapping("/image")
    public ResponseEntity<?> getImage(@RequestParam String idEmployee){
        return faceApiService.getImage(idEmployee);
    }
}
