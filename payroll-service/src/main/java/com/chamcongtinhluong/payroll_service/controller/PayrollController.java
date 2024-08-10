package com.chamcongtinhluong.payroll_service.controller;

import com.chamcongtinhluong.payroll_service.dto.request.PayrollRequest;
import com.chamcongtinhluong.payroll_service.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/payroll")
public class PayrollController {

    @Autowired
    PayrollService payrollService;

    @GetMapping
    private ResponseEntity<?> getPayRolls(){
        return payrollService.getListSalary();
    }

    @GetMapping("getidemployee")
    private ResponseEntity<?> getIdemployee(){
        return  payrollService.getIdEmployee();
    }

    @GetMapping("/{idemployee}")
    public  ResponseEntity<?> getPayrollById(@PathVariable String idemployee){
        return payrollService.getPayrollByIdEmployee(idemployee);
    }

    @GetMapping("/totalPayment")
    public int getTotalPayment(@RequestParam int month, @RequestParam int year){
        return payrollService.totalPaymentInMonth(month,year);
    }

    @GetMapping("/getMonthlyEmployee")
    public List<Integer> getTotalSalary(@RequestParam String idemployee, @RequestParam int year){
        return payrollService.getMonthlyTotals(idemployee, year);
    }
    @PostMapping
    private ResponseEntity<?> addPayroll(@RequestBody PayrollRequest payrollRequest){
        return payrollService.payrollEmployee(payrollRequest);
    }

    @PutMapping
    private ResponseEntity<?> updatePayroll(@RequestBody PayrollRequest payrollRequest){
        return payrollService.updatePayroll(payrollRequest);
    }
}
