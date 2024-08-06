package com.chamcongtinhluong.employee.service;

import com.chamcongtinhluong.employee.dto.EmployeeDTO;
import com.chamcongtinhluong.employee.entity.Employee;
import org.springframework.http.ResponseEntity;

public interface EmployeeService {
    public ResponseEntity<?> getEmployee();
    public ResponseEntity<?> getIDEmployee(String id);
    public ResponseEntity<?> addEmployee(EmployeeDTO e);
    public ResponseEntity<?> updateEmployee(String idemployee,EmployeeDTO e);
    public ResponseEntity<?> updateStatusEmployee(String idemployee);
    public ResponseEntity<?> deleteEmployee(String idemployee);
    public String generateEmployeeId();
    public String getDetailSalary(String idemployee);
    public ResponseEntity<?> getEmployeeActive();
    public ResponseEntity<?> countEmployee();
}
