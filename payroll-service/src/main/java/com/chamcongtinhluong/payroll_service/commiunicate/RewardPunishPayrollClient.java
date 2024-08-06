package com.chamcongtinhluong.payroll_service.commiunicate;

import com.chamcongtinhluong.payroll_service.dto.request.RewardPunishPayrollRequest;
import com.chamcongtinhluong.payroll_service.dto.response.RewardPunishPayrollResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "rewardpunish-service", url = "http://localhost:8086/api/v1/rewardpunish")
public interface RewardPunishPayrollClient {
    @PostMapping("/calsalary")
    List<RewardPunishPayrollResponse> getRewardPunishForCalSalary(@RequestBody RewardPunishPayrollRequest rewardPunishPayrollRequest);

}
