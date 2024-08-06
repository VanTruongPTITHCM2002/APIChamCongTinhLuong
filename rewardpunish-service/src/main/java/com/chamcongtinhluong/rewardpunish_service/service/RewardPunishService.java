package com.chamcongtinhluong.rewardpunish_service.service;


import com.chamcongtinhluong.rewardpunish_service.dto.request.RewardPunishPayrollRequest;
import com.chamcongtinhluong.rewardpunish_service.dto.response.RewardPunishPayrollResponse;
import com.chamcongtinhluong.rewardpunish_service.dto.response.RewardPunishResponse;
import com.chamcongtinhluong.rewardpunish_service.entity.RewardPunish;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RewardPunishService {
    public ResponseEntity<?> getRewardPunish();
    public ResponseEntity<?> getRewardPunishByIdemployee(String idemployee);
    public ResponseEntity<?>addRewardPunish(RewardPunishResponse rewardPunishResponse);
    public ResponseEntity<?>deleteRewardPunish(RewardPunishResponse rewardPunishResponse);
    public List<RewardPunishPayrollResponse> calculateSalary(RewardPunishPayrollRequest rewardPunishPayrollRequest);
    public List<List<Integer>> getMonthlyCashTotalsByYearAndType(int year);
}
