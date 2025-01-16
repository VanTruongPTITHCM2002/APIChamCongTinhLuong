package com.chamcongtinhluong.workschedule.controller;


import com.chamcongtinhluong.workschedule.dto.DateRequest;
import com.chamcongtinhluong.workschedule.dto.WorkScheduleDetailRequest;
import com.chamcongtinhluong.workschedule.entity.WorkSchedule;
import com.chamcongtinhluong.workschedule.service.WorkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/api/v1/workschedule")
public class WorkScheduleController {
    @Autowired
    private WorkScheduleService workScheduleService;

    @GetMapping
    private ResponseEntity<?> getAllWorkSchedule(){
        return workScheduleService.getWorkSchedule();
    }

    @GetMapping("/getidemp")
    private ResponseEntity<?> getIDEmployeeClient(@RequestParam Date date){
        return workScheduleService.getIDEmp(date);
    }

    @GetMapping("/{idemployee}")
    private ResponseEntity<?> getEmployeeClient(@PathVariable String idemployee){
        return workScheduleService.getEmp(idemployee);
    }



    @PostMapping("/workdate")
    private ResponseEntity<?> getEmployeDateWork(@RequestBody DateRequest date){
        return workScheduleService.getEmployeeWorkSchedule(date);
    }

    @PostMapping
    private  ResponseEntity<?> addWorkSchedule(@RequestBody WorkSchedule workSchedule){
        return workScheduleService.addWorkSchedule(workSchedule);
    }

    @PostMapping("/many")
    private ResponseEntity<?> addWorkScheduleMany(@RequestParam int month, @RequestParam int year){
        return  workScheduleService.addWorkScheduleEmployeeByMonthYear(month,year);
    }


    @PostMapping("/workschedulemployee")
    private ResponseEntity<?> addWorkScheduleEmployee(@RequestBody WorkScheduleDetailRequest workScheduleDetailRequest){
        return workScheduleService.addWorkScheduleEmployee(workScheduleDetailRequest);
    }

    @DeleteMapping
    private  ResponseEntity<?> deleteWorkSchedule(@RequestParam Date date){
        return workScheduleService.deleteWorkSchedule(date);
    }

}
