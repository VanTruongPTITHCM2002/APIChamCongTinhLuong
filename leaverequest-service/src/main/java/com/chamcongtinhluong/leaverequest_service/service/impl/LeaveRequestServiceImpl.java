package com.chamcongtinhluong.leaverequest_service.service.impl;

import com.chamcongtinhluong.leaverequest_service.Enum.LeaveType;
import com.chamcongtinhluong.leaverequest_service.dto.request.LeaveRequest_Request;
import com.chamcongtinhluong.leaverequest_service.dto.response.ApiResponse;
import com.chamcongtinhluong.leaverequest_service.dto.response.LeaveRequest_Response;
import com.chamcongtinhluong.leaverequest_service.entity.LeaveRequest;
import com.chamcongtinhluong.leaverequest_service.mapper.LeaveRequestMapper;
import com.chamcongtinhluong.leaverequest_service.repository.LeaveRequestRepository;
import com.chamcongtinhluong.leaverequest_service.service.LeaveRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;

    @Override
    public ResponseEntity<ApiResponse> getLeaveRequests() {
        try{
            List<LeaveRequest_Response> leaveRequestResponseList = leaveRequestRepository
                    .findAll().stream().map(
                            LeaveRequestMapper.INSTANCE::toResponse
                    ).toList();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.builder()
                            .status(HttpStatus.OK.value())
                            .message("Lay thanh cong danh sach nghi phep")
                            .data(leaveRequestResponseList)
                            .build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Loi ket noi den co du lieu")
                            .build());
        }
    }

    @Override
    public ResponseEntity<ApiResponse> addLeaveRequest(LeaveRequest_Request leaveRequest_request) {
        try{
            LeaveRequest leaveRequest =LeaveRequestMapper.INSTANCE.toEntity(leaveRequest_request);
            leaveRequest.setCreateAt(new Date());
            leaveRequestRepository.save(leaveRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.builder()
                            .status(HttpStatus.CREATED.value())
                            .message("Thêm thành công")
                            .build());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Loi ket noi den co du lieu")
                            .build());
        }
    }

    @Override
    public ResponseEntity<ApiResponse> updateLeaveRequest(String idEmployee, String leaveType, Date createdAt, LeaveRequest_Request leaveRequest_request) {
        try{
            LeaveRequest leaveRequest = leaveRequestRepository.findByIdEmployeeAndStartDateAndLeaveType(
                    idEmployee,createdAt, LeaveType.convertStringToEnum(leaveType));

            if(leaveRequest == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        ApiResponse.builder()
                                .status(HttpStatus.NOT_FOUND.value())
                                .message("Khong tim thay nghi phep")
                                .build()
                );
            }
            int id = leaveRequest.getIdLeaveRequest();
            leaveRequest = LeaveRequestMapper.INSTANCE.toEntity(leaveRequest_request);
            leaveRequest.setApproveAt(new Date());
            leaveRequest.setIdLeaveRequest(id);
            leaveRequestRepository.save(leaveRequest);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Loi ket noi den co so du lieu...")
                            .build()
            );
        }

        return ResponseEntity.ok().body(ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Cap nhat thanh cong nghi phep")
                .build());
    }

    @Override
    public ResponseEntity<ApiResponse> deleteLeaveRequest(LeaveRequest_Request leaveRequest_request) {
        try {
            LeaveRequest leaveRequest = leaveRequestRepository.findByIdEmployeeAndStartDateAndLeaveType(
                    leaveRequest_request.getIdEmployee(), leaveRequest_request.getStartDate(), LeaveType.convertStringToEnum(leaveRequest_request.getLeaveType()));

            if (leaveRequest == null) {
                throw new NullPointerException("");
            }
           leaveRequestRepository.delete(leaveRequest);
            return ResponseEntity.ok().body(ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Xoa thanh cong nghi phep")
                    .build());
        }catch (NullPointerException nullPointerException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("Khong tim thay nghi phep")
                            .build()
            );
        }
        catch (Exception e){
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Loi ket noi den co so du lieu...")
                            .build()
            );
        }


    }
}
