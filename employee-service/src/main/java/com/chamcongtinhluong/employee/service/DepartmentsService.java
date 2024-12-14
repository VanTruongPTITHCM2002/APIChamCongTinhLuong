package com.chamcongtinhluong.employee.service;

import com.chamcongtinhluong.employee.dto.DepartmentsDTO;
import org.springframework.http.ResponseEntity;

public interface DepartmentsService {
    ResponseEntity<?> getDepartments();
    ResponseEntity<?> getAllDepartmens();
    ResponseEntity<?> getDepartmentNameByIdEmployee(String idEmployee);
    ResponseEntity<?>addDepartment(DepartmentsDTO departmentsDTO);
    ResponseEntity<?> updateDepartment(String departmentCode,DepartmentsDTO departmentsDTO);
    ResponseEntity<?> deleteDepartment(String departmentCode);
}
