package com.chamcongtinhluong.rewardpunish_service.commiunicate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@FeignClient(name = "workschedule-service", url = "http://localhost:8084/api/v1/workscheduledetail")
public interface WorkScheduleServiceClient {
    @PostMapping
    public Boolean getWorkScheduleDetailByIdAndDate(@RequestParam String idemployee, @RequestParam("date") @DateTimeFormat(pattern = "yyyy/MM/dd") Date date);
}
