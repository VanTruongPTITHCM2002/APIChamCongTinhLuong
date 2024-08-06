package com.chamcongtinhluong.rewardpunish_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RewardPunishPayrollRequest {
    private String idemployee;
    private int month;
    private int year;
}
