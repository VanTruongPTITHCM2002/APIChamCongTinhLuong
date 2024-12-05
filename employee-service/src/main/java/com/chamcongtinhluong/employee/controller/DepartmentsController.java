package com.chamcongtinhluong.employee.controller;

import com.chamcongtinhluong.employee.service.DepartmentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentsController {
    private final DepartmentsService departmentsService;

    @GetMapping
    private ResponseEntity<?> getDepartments(){
        return departmentsService.getDepartments();
    }

    @GetMapping("/{idEmployee}")
    private ResponseEntity<?> getDepartmentNameByIdEmployee(@PathVariable String idEmployee){
        return departmentsService.getDepartmentNameByIdEmployee(idEmployee);
    }
}
