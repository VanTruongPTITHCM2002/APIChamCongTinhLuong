package com.chamcongtinhluong.employee.controller;

import com.chamcongtinhluong.employee.dto.EmployeeDTO;
import com.chamcongtinhluong.employee.entity.Employee;
import com.chamcongtinhluong.employee.exception.InvalidEmployeeException;
import com.chamcongtinhluong.employee.respone.ResponeObject;
import com.chamcongtinhluong.employee.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

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
    @GetMapping("/generateId")
    public ResponseEntity<?> getIdempployee(){
        return ResponseEntity.ok().body(new ResponeObject(HttpStatus.OK.value(), "Generate id success",employeeService.generateEmployeeId()));
    }

//    Admin and User
    @GetMapping("/{idemployee}")
    public ResponseEntity<?> getIDEmployee(@PathVariable String idemployee){
        return employeeService.getIDEmployee(idemployee);
    }

    @GetMapping("/detail-salary/{idemployee}")
    public  String getDetailSalary(@PathVariable String idemployee){
        return employeeService.getDetailSalary(idemployee);
    }

//    Admin
    @GetMapping("/amount")
    public ResponseEntity<?> getAmountEmployee(){
        return employeeService.countEmployee();
    }

//    Admin
    @PostMapping
    @Transactional
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDTO e){
        return employeeService.addEmployee(e);
    }

//    Admin and user
    @PutMapping("{idemployee}")
    public ResponseEntity<?>updateEmployee(@PathVariable String idemployee, @RequestBody EmployeeDTO e){
        return employeeService.updateEmployee(idemployee,e);
    }

//    Admin
    @PutMapping("/{idemployee}/changeStatus")
    public ResponseEntity<?>updateStatusEmployee(@PathVariable String idemployee){
        return employeeService.updateStatusEmployee(idemployee);
    }

//    Admin
    @DeleteMapping("/{idemployee}")
    public  ResponseEntity<?>deleteEmployee(@PathVariable String idemployee){
        return  employeeService.deleteEmployee(idemployee);
    }
}
