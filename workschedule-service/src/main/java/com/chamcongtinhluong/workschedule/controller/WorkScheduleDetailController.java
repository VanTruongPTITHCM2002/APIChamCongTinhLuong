package com.chamcongtinhluong.workschedule.controller;

import com.chamcongtinhluong.workschedule.dto.WorkScheduleDetailRequest;
import com.chamcongtinhluong.workschedule.service.WorkScheduleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/api/v1/workscheduledetail")
public class WorkScheduleDetailController {

    @Autowired
    private WorkScheduleDetailService workScheduleDetailService;

    @GetMapping
    private ResponseEntity<?> getAllWorkScheduleDetails(){
        return workScheduleDetailService.getWorkScheduleDetail();
    }

    @GetMapping("/{idemployee}")
    private ResponseEntity<?> getWorkScheduleDetailsById(@PathVariable String idemployee){
        return workScheduleDetailService.getWorkScheduleDetailById(idemployee);
    }

    @PostMapping
    private Boolean getWorkScheduleDetailByIdAndDate(@RequestParam String idemployee,@RequestParam Date date){
        return workScheduleDetailService.getWorkScheduleByIdAndDate(idemployee,date);
    }

    @PutMapping("/{idemployee}")
    private  ResponseEntity<?> updateWorkScheduleDetail(@PathVariable String idemployee,@RequestBody WorkScheduleDetailRequest workScheduleDetailRequest){
        return workScheduleDetailService.updateWorkScheduleDetail(idemployee, workScheduleDetailRequest);
    }

    @DeleteMapping
    private ResponseEntity<?>deleteWorkScheduleDetail(@RequestParam String idemployee,@RequestParam Date date){
        return workScheduleDetailService.deleteWorkScheduleDetail(idemployee, date);
    }
}
