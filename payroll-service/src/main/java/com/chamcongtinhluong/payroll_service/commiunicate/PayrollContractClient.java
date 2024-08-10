package com.chamcongtinhluong.payroll_service.commiunicate;

import com.chamcongtinhluong.payroll_service.dto.request.PayrollContractRequest;
import com.chamcongtinhluong.payroll_service.dto.response.PayrollContractResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@FeignClient(name = "contract-service", url = "http://localhost:8087/api/v1/contract")
public interface PayrollContractClient {
    @PostMapping("/getcontract")
    public PayrollContractResponse getContractByIdMonthYear(@RequestBody PayrollContractRequest payrollContractRequest);
    @PutMapping("/statusContract")
    public ResponseEntity<?> changeStatusContract(@RequestParam String idemployee, @RequestParam int month, @RequestParam int year);
    @GetMapping("/checkcontract")
    Boolean checkContractById(@RequestParam  String idemployee,
                              @RequestParam("date") @DateTimeFormat(pattern = "yyyy/MM/dd") Date date);
}
