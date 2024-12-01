package com.chamcongtinhluong.payroll_service.commiunicate;

import com.chamcongtinhluong.payroll_service.dto.request.DayWorkRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@FeignClient(name = "account-service", url = "http://localhost:8080/api/v1/employee")
public interface DetailSalaryServiceClient {

    @GetMapping("/detail-salary/{idEmployee}")
    String getDetailSalary(@PathVariable String idEmployee);


}


