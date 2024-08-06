package com.chamcongtinhluong.workschedule.service;

import com.chamcongtinhluong.workschedule.dto.WorkScheduleDetailRequest;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface WorkScheduleDetailService {
    public ResponseEntity<?> getWorkScheduleDetailById(String idemeployee);
    public ResponseEntity<?> updateWorkScheduleDetail(String idemployee, WorkScheduleDetailRequest workScheduleDetailRequest);
    public ResponseEntity<?> deleteWorkScheduleDetail(String idemployee, Date date);
    Boolean getWorkScheduleByIdAndDate(String idemployee,Date date);
}
