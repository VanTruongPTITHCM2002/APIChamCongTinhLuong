package com.chamcongtinhluong.attendence.communicate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@FeignClient(name = "contract-service", url = "http://localhost:8087/api/v1/contract")
public interface ContractServiceClient {
    @GetMapping("/checkcontract")
    Boolean checkContractById(@RequestParam  String idemployee,
                              @RequestParam("date") @DateTimeFormat(pattern = "yyyy/MM/dd") Date date);
}
