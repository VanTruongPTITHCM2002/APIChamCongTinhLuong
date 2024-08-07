package com.chamcongtinhluong.employee.communicate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "contract-service", url = "http://localhost:8087/api/v1/contract")
public interface ContractEmployeeServiceClient {
    @GetMapping("/checkemployee/{idemployee}")
   Boolean checkEmployee(@PathVariable String idemployee);
}
