package com.chamcongtinhluong.attendence.service;

import com.chamcongtinhluong.attendence.dto.request.AttendanceExplainRequest;
import com.chamcongtinhluong.attendence.dto.request.IdEmployeeRequest;
import com.chamcongtinhluong.attendence.dto.response.AttedanceExplainResponse;
import org.springframework.http.ResponseEntity;

public interface AttendanceExplainService {
    ResponseEntity<?> getAttedanceExplain();
    ResponseEntity<?> addAttendanceExplain(AttendanceExplainRequest attendanceExplainRequest);
    ResponseEntity<?> getAttendanceExplainById(IdEmployeeRequest idEmployeeRequest);
    ResponseEntity<?> updateAttendanceExplain(AttedanceExplainResponse attedanceExplainResponse);
}
