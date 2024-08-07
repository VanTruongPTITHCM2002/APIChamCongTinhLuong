package com.chamcongtinhluong.contract_service.service;

import com.chamcongtinhluong.contract_service.dto.request.ContractRequest;
import com.chamcongtinhluong.contract_service.dto.request.ContractUpdate;
import com.chamcongtinhluong.contract_service.dto.request.PayrollContractRequest;
import com.chamcongtinhluong.contract_service.dto.response.PayrollContractResponse;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface ContractService {
    public ResponseEntity<?> getContracts();
    public ResponseEntity<?> getContractByIdemployee(String idemployee);
    public ResponseEntity<?> addContract(ContractRequest contractRequest);
    public ResponseEntity<?> updateContract(ContractUpdate contractRequestUpdate);
    public ResponseEntity<?> changeStatusContract(String idemployee,int month,int year);
    Boolean checkStatusContract(String idemployee, Date date);
    PayrollContractResponse getBasicSalaryDayWork(PayrollContractRequest payrollContractRequest);
    public void updateContractStatus();
    public int countContracts(int month,int year);
    Boolean checkIdEmployeeInContract(String idemployee);
}
