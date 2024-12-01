package com.chamcongtinhluong.leaverequest_service.controller;

import com.chamcongtinhluong.leaverequest_service.dto.request.LeaveRequest_Request;
import com.chamcongtinhluong.leaverequest_service.dto.response.ApiResponse;
import com.chamcongtinhluong.leaverequest_service.service.LeaveRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/leave-request")
@RequiredArgsConstructor
public class LeaveRequestController {
    private final LeaveRequestService leaveRequestService;

    @GetMapping
    private ResponseEntity<ApiResponse> getLeaveRequests(){
        return leaveRequestService.getLeaveRequests();
    }

    @GetMapping("/user")
    private  ResponseEntity<ApiResponse>getLeaveRequestByUser(@RequestParam String idEmployee){
        return null;
    }

    @GetMapping("/users")
    private ResponseEntity<ApiResponse>getLeaveRequestByUsers(){
        return null;
    }

    @PostMapping
    private ResponseEntity<ApiResponse> addLeaveRequest(@Validated @RequestBody LeaveRequest_Request leaveRequest_request){
        return leaveRequestService.addLeaveRequest(leaveRequest_request);
    }

    @PutMapping
    private ResponseEntity<ApiResponse> updateLeaveRequest(
            @RequestParam String idEmployee,
            @RequestParam String leaveType,
            @RequestParam @DateTimeFormat(pattern = "yyyy/MM/dd") Date createdAt,
            @Validated @RequestBody LeaveRequest_Request leaveRequest_request){
        return leaveRequestService.updateLeaveRequest(idEmployee, leaveType, createdAt, leaveRequest_request);
    }

    @DeleteMapping
    private ResponseEntity<ApiResponse> deleteLeaveRequest( @RequestBody LeaveRequest_Request leaveRequest_request){
        return leaveRequestService.deleteLeaveRequest(leaveRequest_request);
    }
}
