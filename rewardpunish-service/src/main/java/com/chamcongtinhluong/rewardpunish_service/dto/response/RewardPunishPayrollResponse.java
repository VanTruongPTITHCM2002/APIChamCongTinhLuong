package com.chamcongtinhluong.rewardpunish_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RewardPunishPayrollResponse {
    private String idemployee;
    private String type;
    private int cash;
}
