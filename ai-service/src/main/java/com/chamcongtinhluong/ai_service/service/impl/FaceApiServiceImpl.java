package com.chamcongtinhluong.ai_service.service.impl;

import com.chamcongtinhluong.ai_service.communicate.EmployeeServiceClient;
import com.chamcongtinhluong.ai_service.dto.request.FaceApiRequest;
import com.chamcongtinhluong.ai_service.dto.request.FaceRecognizeRequest;
import com.chamcongtinhluong.ai_service.dto.response.ApiResponse;
import com.chamcongtinhluong.ai_service.entity.FaceApi;
import com.chamcongtinhluong.ai_service.repository.FaceApiRepository;
import com.chamcongtinhluong.ai_service.service.FaceApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.ByteBuffer;

@Service
@RequiredArgsConstructor
public class FaceApiServiceImpl implements FaceApiService {

    private final FaceApiRepository faceApiRepository;
    private final EmployeeServiceClient employeeServiceClient;
    @Override
    public ResponseEntity<ApiResponse> saveFaceApi(FaceApiRequest faceApiRequest) {

        Float[] faceDescriptorFloats = faceApiRequest.getFaceDescriptor();

        // Chuyển Float[] thành byte[]
        ByteBuffer buffer = ByteBuffer.allocate(faceDescriptorFloats.length * Float.BYTES);
        for (Float floatValue : faceDescriptorFloats) {
            buffer.putFloat(floatValue);
        }
        byte[] faceDescriptorBytes = buffer.array();
        // Tạo đối tượng FaceData
        FaceApi faceData = faceApiRepository.findByIdEmployee(faceApiRequest.getEmployeeId());
        if(faceData == null){
            faceData = new FaceApi();
            faceData.setIdEmployee(faceApiRequest.getEmployeeId());
            faceData.setFaceDescriptor(faceDescriptorBytes);
            faceApiRepository.save(faceData);
            try {
                employeeServiceClient.uploadFile(faceDescriptorBytes,faceApiRequest.getEmployeeId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        faceData.setFaceDescriptor(faceDescriptorBytes);
        faceApiRepository.save(faceData);
        // Lưu vào database


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Nhan dien khuon mat thanh cong")
                        .build());
    }

    @Override
    public ResponseEntity<ApiResponse> recognizeFaceApi(FaceRecognizeRequest faceRecognizeRequest) {
        Float[] inputDescriptorFloats = faceRecognizeRequest.getFaceDescriptor();
        float[] inputDescriptor = new float[inputDescriptorFloats.length];
        for (int i = 0; i < inputDescriptorFloats.length; i++) {
            inputDescriptor[i] = inputDescriptorFloats[i];
        }

        // Lặp qua các FaceApi đã lưu trong cơ sở dữ liệu
        for (FaceApi faceApi : faceApiRepository.findAll()) {
            // Chuyển byte[] trong FaceApi thành float[]
            float[] savedDescriptor = byteArrayToFloatArray(faceApi.getFaceDescriptor());

            // Tính toán độ tương đồng
            float similarity = euclideanDistance(inputDescriptor, savedDescriptor);

            // Kiểm tra ngưỡng
            if (similarity < 0.6) {
                return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Nhận diện khuôn mặt thành công")
                        .data(faceApi.getIdEmployee())
                        .build());
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("Không tìm thấy khuôn mặt")
                .build());
    }

    @Override
    public ResponseEntity<?> getImage(String idEmployee) {
        FaceApi faceApi = faceApiRepository.findByIdEmployee(idEmployee);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)  // Chỉnh lại loại MIME tùy theo loại ảnh
                .body(faceApi.getFaceDescriptor());
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

}

