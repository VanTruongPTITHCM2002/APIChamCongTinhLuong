package com.chamcongtinhluong.workschedule.repository;

import com.chamcongtinhluong.workschedule.entity.EmployeeWorkScheduleId;
import com.chamcongtinhluong.workschedule.entity.WorkScheduleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WorkScheduleDetailsRepository extends JpaRepository<WorkScheduleDetails, EmployeeWorkScheduleId> {
    WorkScheduleDetails findByEmployeeWorkScheduleIdWorkscheduleAndEmployeeWorkScheduleIdIdemployee(int workschedule, String idemployee);
}
