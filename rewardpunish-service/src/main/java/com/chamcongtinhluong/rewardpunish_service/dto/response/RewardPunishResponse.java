package com.chamcongtinhluong.rewardpunish_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RewardPunishResponse {
    private String idemployee;
    private String type;
    private int cash;
    private String reason;
    private Date setupdate;
    private String status;
}
