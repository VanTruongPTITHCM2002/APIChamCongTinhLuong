package com.chamcongtinhluong.attendence.repository;

import com.chamcongtinhluong.attendence.entity.Attendance;
import com.chamcongtinhluong.attendence.entity.WorkRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkRecordRepository extends JpaRepository<WorkRecord,Integer> {
    WorkRecord findByMonthAndYear(int month, int year);
    @Query("SELECT a FROM WorkRecord a WHERE " +
            "(a.idemployee = :idemployee)")
    List<WorkRecord> filterWorkRecord(
            @Param("idemployee") String idemployee);
    WorkRecord findByIdemployeeAndMonthAndYear(String idemployee, int month, int year);

}
