package com.chamcongtinhluong.employee.controller;

import com.chamcongtinhluong.employee.dto.DepartmentsDTO;
import com.chamcongtinhluong.employee.service.DepartmentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentsController {
    private final DepartmentsService departmentsService;

    @GetMapping
    private ResponseEntity<?> getDepartments(){
        return departmentsService.getDepartments();
    }

    @GetMapping("/code")
    private ResponseEntity<?> getAllDepartments(){
        return departmentsService.getAllDepartmens();
    }
    @GetMapping("/{idEmployee}")
    private ResponseEntity<?> getDepartmentNameByIdEmployee(@PathVariable String idEmployee){
        return departmentsService.getDepartmentNameByIdEmployee(idEmployee);
    }

    @PostMapping
    private ResponseEntity<?> addDepartment(@RequestBody DepartmentsDTO departmentsDTO){
        return departmentsService.addDepartment(departmentsDTO);
    }

    @PutMapping
    private  ResponseEntity<?> updateDepartment(@RequestParam String departmentCode,@RequestBody DepartmentsDTO departmentsDTO){
        return departmentsService.updateDepartment(departmentCode,departmentsDTO);
    }

    @DeleteMapping
    private  ResponseEntity<?> deleteDepartment(@RequestParam String departmentCode){
        return departmentsService.deleteDepartment(departmentCode);
    }
}
