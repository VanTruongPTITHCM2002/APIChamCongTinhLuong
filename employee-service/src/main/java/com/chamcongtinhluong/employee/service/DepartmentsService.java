package com.chamcongtinhluong.employee.service;

import org.springframework.http.ResponseEntity;

public interface DepartmentsService {
    ResponseEntity<?> getDepartments();
    ResponseEntity<?> getDepartmentNameByIdEmployee(String idEmployee);
}
