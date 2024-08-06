package com.chamcongtinhluong.contract_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractUpdate {
    private ContractRequest oldContractReqeust;
    private ContractRequest newContractRequest;
}
