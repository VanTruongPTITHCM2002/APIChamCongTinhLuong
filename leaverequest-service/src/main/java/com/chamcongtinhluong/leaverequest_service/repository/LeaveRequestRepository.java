package com.chamcongtinhluong.leaverequest_service.repository;

import com.chamcongtinhluong.leaverequest_service.Enum.LeaveType;
import com.chamcongtinhluong.leaverequest_service.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,Integer> {
    LeaveRequest findByIdEmployeeAndStartDateAndLeaveType(String idEmployee, Date startDate, LeaveType leaveType);
}
