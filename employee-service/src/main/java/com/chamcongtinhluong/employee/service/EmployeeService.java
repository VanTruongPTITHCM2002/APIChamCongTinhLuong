package com.chamcongtinhluong.employee.service;


import com.chamcongtinhluong.employee.dto.request.EmployeeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface EmployeeService {
    public ResponseEntity<?> getEmployee();
    public ResponseEntity<?> getEmployeeDepartments(String departments);
    public ResponseEntity<?> getIDEmployee(String id);
    public ResponseEntity<?> addEmployee(EmployeeRequest e);
    public ResponseEntity<?> updateEmployee(String idemployee,EmployeeRequest e);
    public ResponseEntity<?> updateStatusEmployee(String idemployee);
    public ResponseEntity<?> deleteEmployee(String idemployee);
    public String getDetailSalary(String idemployee);
    public ResponseEntity<?> getEmployeeActive();
    public ResponseEntity<?> countEmployee();
    public ResponseEntity<?> uploadFileEmployee(MultipartFile multipartFile,String idEmployee) throws IOException;
    public ResponseEntity<?> uploadImageEmployee(byte[] imageEmployee,String idEmployee);
    public ResponseEntity<?> getImageEmployee(String idEmployee);
    int getNumberSalary(String idEmployee);
}
