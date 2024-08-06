package com.chamcongtinhluong.employee.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailSalaryResponse {
    private String idemployee;
    private String name;
    private int total;
}
