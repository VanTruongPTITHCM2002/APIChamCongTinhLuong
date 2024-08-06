package com.chamcongtinhluong.attendence.controller;

import com.chamcongtinhluong.attendence.dto.request.AttendanceRequest;
import com.chamcongtinhluong.attendence.dto.request.IdEmployeeRequest;
import com.chamcongtinhluong.attendence.dto.response.AttendanceResponse;
import com.chamcongtinhluong.attendence.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

//    ADMIN
    @GetMapping
    private ResponseEntity<?> getAttendance(){
        return attendanceService.getAttendance();
    }
//USER
    @GetMapping("/{idemployee}")
    private ResponseEntity<?>getAttendanceById(@PathVariable String idemployee){
        return attendanceService.getAttendanceById(idemployee);
    }
//ADMIN
    @GetMapping("/countDate")
    private int countDate(@RequestParam Date date){
        return  attendanceService.countAttendanceByDate(date);
    }
//USER
    @PostMapping
    private ResponseEntity<?>addAttendance (@RequestBody AttendanceRequest attendanceRequest) throws ParseException {
        return attendanceService.addAttendance(attendanceRequest);
    }

//    ADMIN
    @PostMapping("/filter")
    private ResponseEntity<?> getFilterAttendance(@RequestBody IdEmployeeRequest idEmployeeRequest){
        return attendanceService.getFilterAttendance(idEmployeeRequest);
    }
//USER
    @PutMapping
    private ResponseEntity<?>updateAttendance(@RequestBody AttendanceRequest attendanceRequest) throws ParseException{

        return attendanceService.updateAttendance(attendanceRequest);
    }
//ADMIN
    @PutMapping("/admin")
    private  ResponseEntity<?>updateAttedanceByAdmin(@RequestBody AttendanceRequest attendanceRequest) throws ParseException{
        return attendanceService.updateAttendanceByAdmin(attendanceRequest);
    }

    @PostMapping("/admin/add-attendance")
    private  ResponseEntity<?>addAttendanceByAdmin(@RequestBody AttendanceRequest attendanceRequest) throws ParseException{
        return attendanceService.addAttendanceByAdmin(attendanceRequest);
    }
}
