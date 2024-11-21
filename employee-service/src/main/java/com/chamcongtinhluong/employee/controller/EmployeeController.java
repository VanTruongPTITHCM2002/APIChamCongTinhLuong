package com.chamcongtinhluong.employee.controller;

import com.chamcongtinhluong.employee.dto.request.EmployeeRequest;
import com.chamcongtinhluong.employee.dto.response.ResponeObject;
import com.chamcongtinhluong.employee.service.EmployeeService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

//    Admin
    @GetMapping
    public ResponseEntity<?>getAllEmployee(){
        return employeeService.getEmployee();
    }

//    Admin
    @GetMapping("/list")
    public ResponseEntity<?> getEmployees(){
        return employeeService.getEmployeeActive();
    }
// Admin
//    @GetMapping("/generateId")
//    public ResponseEntity<?> getIdempployee(){
//        return ResponseEntity.ok().body(
//                new ResponeObject(HttpStatus.OK.value(), "Generate id success",employeeService.generateEmployeeId()));
//    }

//    Admin and User
    @GetMapping("/{idEmployee}")
    public ResponseEntity<?> getIDEmployee(@PathVariable String idEmployee){
        return employeeService.getIDEmployee(idEmployee);
    }

    @GetMapping("/departments")
    public ResponseEntity<?> getEmployeeDepartments(@RequestParam String department){
        return employeeService.getEmployeeDepartments(department);
    }

    @GetMapping("/detail-salary/{idEmployee}")
    public  String getDetailSalary(@PathVariable String idEmployee){
        return employeeService.getDetailSalary(idEmployee);
    }

    @GetMapping("/{idEmployee}/image")
    public ResponseEntity<?> getImageEmployee(@PathVariable String idEmployee){
        return employeeService.getImageEmployee(idEmployee);
    }

//    Admin
    @GetMapping("/amount")
    public ResponseEntity<?> getAmountEmployee(){
        return employeeService.countEmployee();
    }

//    Admin
    @PostMapping
//    @Transactional
    public ResponseEntity<?> addEmployee(@RequestBody @Valid EmployeeRequest e){
        return employeeService.addEmployee(e);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("image")MultipartFile multipartFile,
                                        @RequestParam("idEmployee") String idEmployee) throws IOException {
        return employeeService.uploadFileEmployee(multipartFile,idEmployee);
    }

//    Admin and user
    @PutMapping("{idEmployee}")
    public ResponseEntity<?>updateEmployee(@PathVariable String idEmployee, @RequestBody EmployeeRequest e){
        return employeeService.updateEmployee(idEmployee,e);
    }

//    Admin
    @PutMapping("/{idEmployee}/changeStatus")
    public ResponseEntity<?>updateStatusEmployee(@PathVariable String idEmployee){
        return employeeService.updateStatusEmployee(idEmployee);
    }

//    Admin
    @DeleteMapping("/{idEmployee}")
    public  ResponseEntity<?>deleteEmployee(@PathVariable String idEmployee){
        return  employeeService.deleteEmployee(idEmployee);
    }
}
