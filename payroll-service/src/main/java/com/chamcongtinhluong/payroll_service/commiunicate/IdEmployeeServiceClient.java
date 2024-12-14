package com.chamcongtinhluong.payroll_service.commiunicate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "employee-service", url = "http://localhost:8080/api/v1/employee")
public interface IdEmployeeServiceClient {
    @GetMapping("/list")
    public ResponseEntity<?> getEmployees();
    @PostMapping("/getNumberSal")
    public int getNumberSalary(@RequestParam String idEmployee);
}
