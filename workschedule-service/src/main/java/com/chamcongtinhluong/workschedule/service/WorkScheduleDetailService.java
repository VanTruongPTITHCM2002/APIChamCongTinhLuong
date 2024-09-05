package com.chamcongtinhluong.workschedule.service;

import com.chamcongtinhluong.workschedule.dto.WorkScheduleDetailRequest;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface WorkScheduleDetailService {
    public ResponseEntity<?> getWorkScheduleDetail();
    public ResponseEntity<?> getWorkScheduleDetailById(String idemeployee);
    public ResponseEntity<?> addWorkScheduleManyEmployee(List<String> listEmployee);
    public ResponseEntity<?> updateWorkScheduleDetail(String idemployee, WorkScheduleDetailRequest workScheduleDetailRequest);
    public ResponseEntity<?> deleteWorkScheduleDetail(String idemployee, Date date);
    Boolean getWorkScheduleByIdAndDate(String idemployee,Date date);
}
