package com.chamcongtinhluong.payroll_service.service;

import com.chamcongtinhluong.payroll_service.dto.request.PayrollRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PayrollService {
    public ResponseEntity<?>getListSalary();
    public ResponseEntity<?>getPayrollByIdEmployee(String idemployee);
    public ResponseEntity<?>payrollEmployee(PayrollRequest payrollRequest);
    public ResponseEntity<?>getIdEmployee();
    public ResponseEntity<?>updatePayroll(PayrollRequest payrollRequest);
    public int totalPaymentInMonth(int month,int year);
    public List<Integer> getMonthlyTotals(String idemployee, int year);
}
