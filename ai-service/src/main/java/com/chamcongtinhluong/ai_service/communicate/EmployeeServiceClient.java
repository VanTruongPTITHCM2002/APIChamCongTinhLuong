package com.chamcongtinhluong.ai_service.communicate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@FeignClient(name = "ai-service-employee-service", url = "http://localhost:8080/api/v1/employee")
public interface EmployeeServiceClient {
    @PostMapping("/upload/imageEmployee")
    public ResponseEntity<?> uploadFile(@RequestParam("image")byte[] imageEmployee,
                                        @RequestParam("idEmployee") String idEmployee) throws IOException;
}
