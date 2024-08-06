package com.chamcongtinhluong.workschedule.service;


import com.chamcongtinhluong.workschedule.dto.DateRequest;
import com.chamcongtinhluong.workschedule.dto.WorkScheduleDetailRequest;
import com.chamcongtinhluong.workschedule.entity.WorkSchedule;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface WorkScheduleService {
    public ResponseEntity<?> getWorkSchedule();
    public ResponseEntity<?> addWorkSchedule(WorkSchedule workSchedule);
    public ResponseEntity<?> getEmp(String id);
    public ResponseEntity<?> getIDEmp(Date date);
    public ResponseEntity<?> addWorkScheduleEmployee(WorkScheduleDetailRequest workScheduleDetailRequest);
    public ResponseEntity<?> getEmployeeWorkSchedule(DateRequest date);
    public ResponseEntity<?> deleteWorkSchedule(Date date);
}
