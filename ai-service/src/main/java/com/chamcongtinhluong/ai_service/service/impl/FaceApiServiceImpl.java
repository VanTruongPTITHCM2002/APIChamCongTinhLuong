package com.chamcongtinhluong.ai_service.service.impl;

import com.chamcongtinhluong.ai_service.dto.request.FaceApiRequest;
import com.chamcongtinhluong.ai_service.dto.request.FaceRecognizeRequest;
import com.chamcongtinhluong.ai_service.dto.response.ApiResponse;
import com.chamcongtinhluong.ai_service.entity.FaceApi;
import com.chamcongtinhluong.ai_service.repository.FaceApiRepository;
import com.chamcongtinhluong.ai_service.service.FaceApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;

@Service
@RequiredArgsConstructor
public class FaceApiServiceImpl implements FaceApiService {

    private final FaceApiRepository faceApiRepository;

    @Override
    public ResponseEntity<ApiResponse> saveFaceApi(FaceApiRequest faceApiRequest) {

        byte[] faceDescriptor = new byte[faceApiRequest.getFaceDescriptor().length];
        for (int i = 0; i < faceApiRequest.getFaceDescriptor().length; i++) {
            faceDescriptor[i] = faceApiRequest.getFaceDescriptor()[i].byteValue();
        }

        // Tạo đối tượng FaceData
        FaceApi faceData = new FaceApi();
        faceData.setIdEmployee(faceApiRequest.getEmployeeId());
        faceData.setFaceDescriptor(faceDescriptor);

        // Lưu vào database
        faceApiRepository.save(faceData);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Nhan dien khuon mat thanh cong")
                        .build());
    }

    @Override
    public ResponseEntity<ApiResponse> recognizeFaceApi(FaceRecognizeRequest faceRecognizeRequest) {
        byte[] inputDescriptor = toByteArray(faceRecognizeRequest.getFaceDescriptor());
        FaceApi faceApi = faceApiRepository.findByIdEmployee(faceRecognizeRequest.getIdEmployee());
        float similarity = compareDescriptors(inputDescriptor, faceApi.getFaceDescriptor());
        if (similarity < 0.6) {
                return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Nhan dien khuon mat thanh cong")
                                .data(faceApi.getIdEmployee())
                        .build());

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message("Khong tim thay khuon mat")
                .build());
    }

    public float compareDescriptors(byte[] inputDescriptor, byte[] savedDescriptor) {
        // Chuyển byte[] thành float[] hoặc double[]
        float[] inputVector = byteArrayToFloatArray(inputDescriptor);
        float[] savedVector = byteArrayToFloatArray(savedDescriptor);

        // Tính toán Euclidean distance
        return euclideanDistance(inputVector, savedVector);
    }

    // Hàm chuyển đổi byte[] thành float[]
    private float[] byteArrayToFloatArray(byte[] byteArray) {
        float[] floatArray = new float[byteArray.length / 4];
        for (int i = 0; i < floatArray.length; i++) {
            floatArray[i] = ByteBuffer.wrap(byteArray, i * 4, 4).getFloat();
        }
        return floatArray;
    }

    // Tính Euclidean distance giữa 2 vector
    private float euclideanDistance(float[] vectorA, float[] vectorB) {
        if (vectorA.length != vectorB.length) {
            System.out.println("Lỗi: Các vector có độ dài khác nhau." + vectorA.length + '-' + vectorB.length);
            return -1; // Trả về giá trị mặc định nếu các vector không khớp độ dài
        }

        float sum = 0;
        for (int i = 0; i < vectorA.length; i++) {
            sum += (float) Math.pow(vectorA[i] - vectorB[i], 2);
        }
        return (float) Math.sqrt(sum);
    }

    private byte[] toByteArray(Integer[] descriptor) {
        byte[] byteArray = new byte[descriptor.length * 4];
        for (int i = 0; i < descriptor.length; i++) {
            ByteBuffer.wrap(byteArray, i * 4, 4).putFloat(descriptor[i]);
        }
        return byteArray;
    }
}

