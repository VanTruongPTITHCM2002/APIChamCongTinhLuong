package com.chamcongtinhluong.attendence.repository;

import com.chamcongtinhluong.attendence.entity.Attendance;
import com.chamcongtinhluong.attendence.entity.AttendanceExplain;
import com.chamcongtinhluong.attendence.entity.WorkRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AttendanceExplainRepository extends JpaRepository<AttendanceExplain,Integer> {
    @Query("SELECT a FROM AttendanceExplain a WHERE " +
            "(a.attendance.idemployee = :idemployee)")
    List<AttendanceExplain> filterAttendanceExplain(
            @Param("idemployee") String idemployee);
    AttendanceExplain findByAttendance(Attendance attendance);
}
