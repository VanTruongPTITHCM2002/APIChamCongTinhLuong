package com.chamcongtinhluong.attendence.repository;

import com.chamcongtinhluong.attendence.entity.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceStatusRepository extends JpaRepository<AttendanceStatus,Integer> {
}
