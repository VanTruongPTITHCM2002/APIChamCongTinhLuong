package com.chamcongtinhluong.attendence.service;

import com.chamcongtinhluong.attendence.dto.request.WorkRecordIDEmployeeRequest;
import com.chamcongtinhluong.attendence.dto.request.WorkRecordRequest;
import com.chamcongtinhluong.attendence.dto.response.WorkRecordResponse;
import org.springframework.http.ResponseEntity;

public interface WorkRecordService {
    ResponseEntity<?> getWorkRecord();
    ResponseEntity<?> getWorkRecordById(WorkRecordIDEmployeeRequest workRecordIDEmployeeRequest);
    ResponseEntity<?> addWorkRecord(WorkRecordRequest workRecordRequest);
    ResponseEntity<?> getWorkRecord(WorkRecordRequest workRecordRequest);
    WorkRecordResponse getWorkRecordMany(WorkRecordRequest workRecordRequest);
    ResponseEntity<?> getIdemployee();
}
