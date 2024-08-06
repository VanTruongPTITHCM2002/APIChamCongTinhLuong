package com.chamcongtinhluong.workschedule.repository;

import com.chamcongtinhluong.workschedule.entity.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface WorkScheduleRepository extends JpaRepository<WorkSchedule,Integer>{
    Boolean existsByWorkdate(Date date);
    WorkSchedule findByWorkdate(Date date);
}
