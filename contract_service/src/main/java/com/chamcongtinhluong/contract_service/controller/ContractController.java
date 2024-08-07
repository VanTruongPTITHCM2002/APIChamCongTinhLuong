package com.chamcongtinhluong.contract_service.controller;

import com.chamcongtinhluong.contract_service.dto.request.ContractRequest;
import com.chamcongtinhluong.contract_service.dto.request.ContractUpdate;
import com.chamcongtinhluong.contract_service.dto.request.PayrollContractRequest;
import com.chamcongtinhluong.contract_service.dto.response.PayrollContractResponse;
import com.chamcongtinhluong.contract_service.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/contract")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @GetMapping
    private ResponseEntity<?>getContracts(){
        return contractService.getContracts();
    }

    @GetMapping("/{idemployee}")
    private ResponseEntity<?>getContractById(@PathVariable String idemployee){
        return contractService.getContractByIdemployee(idemployee);
    }

    @GetMapping("/checkcontract")
    private  Boolean checkContractById(@RequestParam String idemployee,@RequestParam Date date){
        return contractService.checkStatusContract(idemployee,date);
    }

    @GetMapping("/countContract")
    private int countContractsByMonthAndYear(@RequestParam int month,@RequestParam int year){
        return contractService.countContracts(month, year);
    }

    @GetMapping("/checkemployee/{idemployee}")
    private Boolean checkEmployee(@PathVariable String idemployee){
        return contractService.checkIdEmployeeInContract(idemployee);
    }

    @PostMapping("/getcontract")
    private PayrollContractResponse getContractByIdMonthYear(@RequestBody PayrollContractRequest payrollContractRequest){
        return contractService.getBasicSalaryDayWork(payrollContractRequest);
    }

    @PostMapping
    private ResponseEntity<?>addContract(@RequestBody ContractRequest contractRequest){
        return contractService.addContract(contractRequest);
    }

    @PutMapping
    private ResponseEntity<?>updateContract(@RequestBody ContractUpdate contractUpdate){
        return  contractService.updateContract(contractUpdate);
    }

    @PutMapping("/statusContract")
    private ResponseEntity<?>changeStatusContract(@RequestParam String idemployee,@RequestParam int month,@RequestParam int year){
        return contractService.changeStatusContract(idemployee,month,year);
    }



}
