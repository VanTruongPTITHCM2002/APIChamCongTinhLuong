package com.chamcongtinhluong.attendence.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkRecordRequest {
    private String idemployee;
    private int month;
    private int year;
}
