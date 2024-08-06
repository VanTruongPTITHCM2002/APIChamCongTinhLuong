package com.chamcongtinhluong.contract_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractResponse {
    private String idemployee;
    private int basicsalary;
    private int workingdays;
    private Date startdate;
    private Date endate;
    private String status;

}
