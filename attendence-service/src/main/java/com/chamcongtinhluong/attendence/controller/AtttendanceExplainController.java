package com.chamcongtinhluong.attendence.controller;

import com.chamcongtinhluong.attendence.dto.request.AttendanceExplainRequest;
import com.chamcongtinhluong.attendence.dto.request.IdEmployeeRequest;
import com.chamcongtinhluong.attendence.dto.response.AttedanceExplainResponse;
import com.chamcongtinhluong.attendence.service.AttendanceExplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/attendance_explain")
public class AtttendanceExplainController {

    @Autowired
    AttendanceExplainService attendanceExplainService;

    @GetMapping
    private ResponseEntity<?> getAttendanceExplain(){
        return attendanceExplainService.getAttedanceExplain();
    }

    @PostMapping
    private  ResponseEntity<?> addAttendanceExplain(@RequestBody AttendanceExplainRequest attendanceExplainRequest)
    {
        return attendanceExplainService.addAttendanceExplain(attendanceExplainRequest);
    }

    @PostMapping("/filter")
    private ResponseEntity<?> getAttendanceExplainById(@RequestBody IdEmployeeRequest idEmployeeRequest){
        return attendanceExplainService.getAttendanceExplainById(idEmployeeRequest);
    }

    @GetMapping("/{idemployee}")
    private ResponseEntity<?> getAttendanceExplainByIdemployee(@PathVariable String idemployee){

        return attendanceExplainService.getAttendanceExplainById(IdEmployeeRequest.builder().idemployee(idemployee).build());
    }

    @PutMapping
    private ResponseEntity<?> updateAttendanceExplain(@RequestBody AttedanceExplainResponse attedanceExplainResponse){
        return attendanceExplainService.updateAttendanceExplain(attedanceExplainResponse);
    }

}
