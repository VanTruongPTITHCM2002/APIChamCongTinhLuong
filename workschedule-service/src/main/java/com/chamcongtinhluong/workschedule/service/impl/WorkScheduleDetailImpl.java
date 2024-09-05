package com.chamcongtinhluong.workschedule.service.impl;

import com.chamcongtinhluong.workschedule.commiunicate.EmployeeServiceClient;
import com.chamcongtinhluong.workschedule.dto.ApiResponse;
import com.chamcongtinhluong.workschedule.dto.WorkScheduleDetailRequest;
import com.chamcongtinhluong.workschedule.entity.EmployeeWorkScheduleId;
import com.chamcongtinhluong.workschedule.entity.WorkSchedule;
import com.chamcongtinhluong.workschedule.entity.WorkScheduleDetails;
import com.chamcongtinhluong.workschedule.repository.WorkScheduleDetailsRepository;
import com.chamcongtinhluong.workschedule.repository.WorkScheduleRepository;
import com.chamcongtinhluong.workschedule.service.WorkScheduleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class WorkScheduleDetailImpl implements WorkScheduleDetailService {

    @Autowired
    private WorkScheduleDetailsRepository workScheduleDetailsRepository;

    @Autowired
    private WorkScheduleRepository workScheduleRepository;

    @Autowired
    private EmployeeServiceClient employeeServiceClient;

    @Override
    public ResponseEntity<?> getWorkScheduleDetail() {
        List<WorkScheduleDetailRequest> workScheduleDetailRequestList = workScheduleDetailsRepository.findAll()

                .stream().map(
                        workScheduleDetails ->
                                WorkScheduleDetailRequest.builder()
                                        .idemployee(workScheduleDetails.getEmployeeWorkScheduleId().getIdemployee())
                                        .workdate(workScheduleDetails.getWorkSchedule().getWorkdate())
                                        .build()
                ).toList();
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Lấy dữ liệu thành công")
                        .data(workScheduleDetailRequestList).build()
        );
    }

    @Override
    public ResponseEntity<?> getWorkScheduleDetailById(String idemeployee) {
        List<WorkScheduleDetailRequest> workScheduleDetailRequestList = workScheduleDetailsRepository
                .findAll().stream().filter(workScheduleDetails -> workScheduleDetails.getEmployeeWorkScheduleId()
                        .getIdemployee().equals(idemeployee))
                .map(e-> new WorkScheduleDetailRequest(e.getEmployeeWorkScheduleId().getIdemployee(),"",e.getWorkSchedule().getWorkdate(), e.getWorkSchedule().getStartime(), e.getWorkSchedule().getEndtime())).toList();
        if(workScheduleDetailRequestList.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiResponse.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Không tìm thấy danh sách làm việc của nhân viên " + idemeployee)
                            .data(workScheduleDetailRequestList).build());
        }
        for(WorkScheduleDetailRequest workScheduleDetailRequest: workScheduleDetailRequestList){
            ResponseEntity<?> response = employeeServiceClient.getIDEmployee(workScheduleDetailRequest.getIdemployee());
            LinkedHashMap employeeResponse = (LinkedHashMap) response.getBody();
            LinkedHashMap employee = (LinkedHashMap) employeeResponse.get("data");
            workScheduleDetailRequest.setName(employee.get("firstname").toString() + " " + employee.get("lastname").toString());
        }
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Lấy thành công danh sách lịch làm việc của nhân viên " + idemeployee)
                        .data(workScheduleDetailRequestList).build()
        );
    }

    @Override
    public ResponseEntity<?> addWorkScheduleManyEmployee(List<String> listEmployee) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateWorkScheduleDetail(String idemployee, WorkScheduleDetailRequest workScheduleDetailRequest) {
        WorkSchedule workSchedule = workScheduleRepository.findByWorkdate(workScheduleDetailRequest.getWorkdate());
        if(workSchedule == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.builder().
                            message("Không thể cập nhật do ngày này không tồn tại trong lịch")
                            .data("").
                            build());
        }
        WorkScheduleDetails workScheduleDetails = workScheduleDetailsRepository.findByEmployeeWorkScheduleIdWorkscheduleAndEmployeeWorkScheduleIdIdemployee(
                workSchedule.getIdwork_schedule(),idemployee
        );


        if(workScheduleDetails == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("Không tìm thấy nhân viên và ngày làm việc này")
                            .data("").build()
            );
        }
        workScheduleDetailsRepository.delete(workScheduleDetails);
        WorkScheduleDetails workScheduleDetails1 = new WorkScheduleDetails();

        workScheduleDetails1.setEmployeeWorkScheduleId(new EmployeeWorkScheduleId(workSchedule.getIdwork_schedule(),workScheduleDetailRequest.getIdemployee()));
        workScheduleDetails1.setWorkSchedule(workSchedule);
        workScheduleDetailsRepository.save(workScheduleDetails1);
         return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Cập nhật thành công nhân viên trong lịch làm việc")
                        .data(workScheduleDetailRequest).build()
        );
    }

    @Override
    public ResponseEntity<?> deleteWorkScheduleDetail(String idemployee, Date date) {
        WorkSchedule workSchedule = workScheduleRepository.findByWorkdate(date);
        if(workSchedule == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.builder().
                            message("Không thể xóa do ngày này không tồn tại trong lịch")
                                    .data("").
                            build());
        }
        WorkScheduleDetails workScheduleDetails = workScheduleDetailsRepository.findByEmployeeWorkScheduleIdWorkscheduleAndEmployeeWorkScheduleIdIdemployee(
                workSchedule.getIdwork_schedule(),idemployee
        );
        if(workScheduleDetails == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("Không tìm thấy nhân viên và ngày làm việc này")
                            .data("").build()
            );
        }

        workScheduleDetailsRepository.delete(workScheduleDetails);
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Xóa thành công nhân viên trong lịch làm việc")
                        .data("").build()
        );
    }

    @Override
    public Boolean getWorkScheduleByIdAndDate(String idemployee, Date date) {
        WorkSchedule workSchedule = workScheduleRepository.findByWorkdate(date);
        if(workSchedule == null){
            return false;
        }
        WorkScheduleDetails workScheduleDetails = workScheduleDetailsRepository.findByEmployeeWorkScheduleIdWorkscheduleAndEmployeeWorkScheduleIdIdemployee(workSchedule.getIdwork_schedule(),idemployee);
        return workScheduleDetails != null;
    }
}
