package com.chamcongtinhluong.payroll_service.service;

import com.chamcongtinhluong.payroll_service.dto.request.PayrollRequest;
import com.chamcongtinhluong.payroll_service.dto.request.PayrollRes;
import com.chamcongtinhluong.payroll_service.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PayrollService {
    public ResponseEntity<?>getListSalary();
    ResponseEntity<ApiResponse> getListSalaryByMonthAndYear(int month,int year);
    public ResponseEntity<?>getPayrollByIdEmployee(String idemployee);
    public ResponseEntity<?>payrollEmployee(PayrollRequest payrollRequest);
    public ResponseEntity<ApiResponse> payrollManyEmployee( PayrollRes payrollRes);
    public ResponseEntity<?>getIdEmployee();
    public ResponseEntity<?>updatePayroll(PayrollRequest payrollRequest);
    public int totalPaymentInMonth(int month,int year);
    public List<Integer> getMonthlyTotals(String idemployee, int year);
}
