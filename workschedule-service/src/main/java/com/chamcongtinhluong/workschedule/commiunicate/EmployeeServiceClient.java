package com.chamcongtinhluong.workschedule.commiunicate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "employee-service", url = "http://localhost:8080/api/v1/employee")
public interface EmployeeServiceClient {

    @GetMapping("/{idEmployee}")
    ResponseEntity<?> getIDEmployee(@PathVariable("idEmployee") String idEmployee);
    @GetMapping("/list")
    ResponseEntity<?> getEmployees();
}
