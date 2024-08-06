package com.chamcongtinhluong.attendence.repository;

import com.chamcongtinhluong.attendence.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Integer> {

    @Query("SELECT a FROM Attendance a WHERE " +
            "(a.idemployee = :idemployee)")
    List<Attendance> filterAttendance(
            @Param("idemployee") String idemployee);

 Attendance findByIdemployeeAndDateattendance(String idemployee, Date dateattendance);

    @Query("SELECT COUNT(p) FROM Attendance p WHERE p.dateattendance = :dateattendance")
    int countByDateattendance(Date dateattendance);
}
