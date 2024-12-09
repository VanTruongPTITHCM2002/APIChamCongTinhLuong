package com.chamcongtinhluong.attendence.controller;

import com.chamcongtinhluong.attendence.dto.request.WorkRecordIDEmployeeRequest;
import com.chamcongtinhluong.attendence.dto.request.WorkRecordRequest;
import com.chamcongtinhluong.attendence.dto.response.WorkRecordResponse;
import com.chamcongtinhluong.attendence.service.WorkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/workrecord")
public class WorkRecordController {

    @Autowired
    WorkRecordService workRecordService;

    @GetMapping
    public ResponseEntity<?> getWorkRecord(){
        return workRecordService.getWorkRecord();
    }

    @GetMapping("/getid")
    public  ResponseEntity<?> getIdemployee(){
        return workRecordService.getIdemployee();
    }

    @PostMapping("/getdaywork")
    public ResponseEntity<?> getDayWork(@RequestBody WorkRecordRequest workRecordRequest){
        return workRecordService.getWorkRecord(workRecordRequest);
    }

    @PostMapping("/getdayworkIdemployee")
    public WorkRecordResponse getDayWorkEmployee(@RequestBody WorkRecordRequest workRecordRequest){
        return workRecordService.getWorkRecordMany(workRecordRequest);
    }

    @PostMapping("/filter")
    public ResponseEntity<?> getWorkRecordById(@RequestBody WorkRecordIDEmployeeRequest workRecordIDEmployeeRequest){
        return workRecordService.getWorkRecordById(workRecordIDEmployeeRequest);
    }

    @PostMapping
    public  ResponseEntity<?> addWorkRecord(@RequestBody WorkRecordRequest workRecordRequest){
        return workRecordService.addWorkRecord(workRecordRequest);
    }

}
