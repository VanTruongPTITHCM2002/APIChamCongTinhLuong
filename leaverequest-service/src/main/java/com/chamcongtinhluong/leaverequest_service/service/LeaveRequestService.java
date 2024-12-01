package com.chamcongtinhluong.leaverequest_service.service;

import com.chamcongtinhluong.leaverequest_service.dto.request.LeaveRequest_Request;
import com.chamcongtinhluong.leaverequest_service.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface LeaveRequestService {
    ResponseEntity<ApiResponse>getLeaveRequests();
    ResponseEntity<ApiResponse>addLeaveRequest(LeaveRequest_Request leaveRequest_request);
    ResponseEntity<ApiResponse>updateLeaveRequest(String idEmployee, String leaveType, Date createdAt, LeaveRequest_Request leaveRequest_request);
    ResponseEntity<ApiResponse>deleteLeaveRequest(LeaveRequest_Request leaveRequest_request);
}
