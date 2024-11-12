package com.chamcongtinhluong.leaverequest_service.repository;

import com.chamcongtinhluong.leaverequest_service.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,Integer> {
}
