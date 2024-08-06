package com.chamcongtinhluong.payroll_service.commiunicate;

import com.chamcongtinhluong.payroll_service.dto.request.DayWorkRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "workrecord-service", url = "http://localhost:8083/api/v1/workrecord")
public interface DayWorkServiceClient {
    @PostMapping("/getdaywork")
    public ResponseEntity<?> getDayWork(@RequestBody DayWorkRequest dayWorkRequest);
}
