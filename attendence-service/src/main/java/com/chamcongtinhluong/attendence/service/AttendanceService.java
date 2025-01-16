package com.chamcongtinhluong.attendence.service;


import com.chamcongtinhluong.attendence.dto.request.AttendanceNewRequest;
import com.chamcongtinhluong.attendence.dto.request.AttendanceRequest;
import com.chamcongtinhluong.attendence.dto.request.IdEmployeeRequest;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.Date;

public interface AttendanceService {
    ResponseEntity<?>getAttendance();
    ResponseEntity<?>getFilterAttendance(IdEmployeeRequest idEmployeeRequest);
    ResponseEntity<?>addAttendance(AttendanceRequest attendanceRequest) throws ParseException;
    ResponseEntity<?>updateAttendance(AttendanceRequest attendanceRequest) throws ParseException;
    ResponseEntity<?>getAttendanceById(String idemployee);
    ResponseEntity<?>updateAttendanceByAdmin(AttendanceRequest attendanceRequest) throws ParseException;
    int countAttendanceByDate(Date date);
    ResponseEntity<?>addAttendanceNew(AttendanceNewRequest attendanceNewRequest) throws ParseException;
    ResponseEntity<?>addAttendanceByAdmin(AttendanceRequest attendanceRequest) throws ParseException;
}
