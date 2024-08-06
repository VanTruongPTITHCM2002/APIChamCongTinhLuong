package com.chamcongtinhluong.contract_service.commiunicate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "employee-service", url = "http://localhost:8080/api/v1/employee")
public interface ContractEmployeeService {
    @PutMapping("/{idemployee}/changeStatus")
    public ResponseEntity<?> updateStatusEmployee(@PathVariable String idemployee);
}
