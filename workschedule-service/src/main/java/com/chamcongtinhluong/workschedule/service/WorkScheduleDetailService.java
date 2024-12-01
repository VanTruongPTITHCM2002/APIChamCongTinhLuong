package com.chamcongtinhluong.workschedule.service;

import com.chamcongtinhluong.workschedule.dto.ListEmployeeRequest;
import com.chamcongtinhluong.workschedule.dto.WorkScheduleDetailRequest;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface WorkScheduleDetailService {
    ResponseEntity<?> getWorkScheduleDetail();
    ResponseEntity<?> getWorkScheduleDetailById(String idemeployee);
    ResponseEntity<?> addWorkScheduleManyEmployee(ListEmployeeRequest listEmployeeRequest);
    ResponseEntity<?> updateWorkScheduleDetail(String idemployee, WorkScheduleDetailRequest workScheduleDetailRequest);
    ResponseEntity<?> deleteWorkScheduleDetail(String idemployee, Date date);
    ResponseEntity<?> deleteWorkScheduleManyEmployee(ListEmployeeRequest listEmployeeRequest);
    Boolean getWorkScheduleByIdAndDate(String idemployee,Date date);
}
