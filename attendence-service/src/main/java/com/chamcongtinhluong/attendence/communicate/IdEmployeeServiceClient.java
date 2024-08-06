package com.chamcongtinhluong.attendence.communicate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "employee-service", url = "http://localhost:8080/api/v1/employee")
public interface IdEmployeeServiceClient {
    @GetMapping("/list")
    public ResponseEntity<?> getEmployees();
}