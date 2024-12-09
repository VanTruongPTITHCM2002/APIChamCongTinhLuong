package com.chamcongtinhluong.attendence.service.impl;

import com.chamcongtinhluong.attendence.communicate.ContractServiceClient;
import com.chamcongtinhluong.attendence.communicate.IdEmployeeClient;
import com.chamcongtinhluong.attendence.communicate.IdEmployeeServiceClient;
import com.chamcongtinhluong.attendence.dto.request.WorkRecordIDEmployeeRequest;
import com.chamcongtinhluong.attendence.dto.request.WorkRecordRequest;
import com.chamcongtinhluong.attendence.dto.response.ApiResponse;
import com.chamcongtinhluong.attendence.dto.response.AttendanceResponse;
import com.chamcongtinhluong.attendence.dto.response.WorkRecordResponse;
import com.chamcongtinhluong.attendence.entity.Attendance;
import com.chamcongtinhluong.attendence.entity.WorkRecord;
import com.chamcongtinhluong.attendence.repository.WorkRecordRepository;
import com.chamcongtinhluong.attendence.service.WorkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class WorkRecordServiceImpl implements WorkRecordService {
    @Autowired
    WorkRecordRepository workRecordRepository;

    @Autowired
    IdEmployeeServiceClient idEmployeeServiceClient;

    @Autowired
    ContractServiceClient contractServiceClient;

    @Override
    public ResponseEntity<?> getWorkRecord() {
        List<WorkRecordResponse> workRecordResponseList = workRecordRepository.findAll()
                .stream().map(e->WorkRecordResponse.builder()
                        .idemployee(e.getIdemployee())
                        .month(e.getMonth())
                        .year(e.getYear())
                        .day_work(e.getDay_work()).build()).toList();
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Lấy thành công bảng ngày công")
                        .data(workRecordResponseList)
                        .build()
        );
    }

    @Override
    public ResponseEntity<?> getWorkRecordById(WorkRecordIDEmployeeRequest workRecordIDEmployeeRequest) {
        if(workRecordIDEmployeeRequest.getIdemployee().equals("")){
            return getWorkRecord();
        }
        List<WorkRecord> workRecordList =
                workRecordRepository.filterWorkRecord(workRecordIDEmployeeRequest.getIdemployee());

        List<WorkRecordResponse> workRecordResponseList =
                workRecordList.stream().map(
                        e->new WorkRecordResponse(
                                e.getIdemployee(),
                               e.getMonth(),
                                e.getYear(),
                                e.getDay_work()
                        )
                ).toList();
        return ResponseEntity.ok().body(ApiResponse.builder().status(HttpStatus.OK.value()).
                message("Lấy dữ liệu bảng công thành công").
                data(workRecordResponseList).build());
    }

    @Override
    public ResponseEntity<?> addWorkRecord(WorkRecordRequest workRecordRequest) {

       WorkRecord workRecordList = workRecordRepository.findByIdemployeeAndMonthAndYear(workRecordRequest.getIdemployee(),workRecordRequest.getMonth(),workRecordRequest.getYear());
        if(workRecordList != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).
                    message("Bảng tổng công của nhân viên đã tồn tại").
                    data("").build());
        }



        workRecordRepository.save(
                WorkRecord.builder()
                        .idemployee(workRecordRequest.getIdemployee())
                        .month(workRecordRequest.getMonth())
                        .year(workRecordRequest.getYear())
                        .day_work(0.0f)
                        .build()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Thêm thành công bảng ghi công")
                        .data("")
                        .build()
        );
    }

    @Override
    public ResponseEntity<?> getWorkRecord(WorkRecordRequest workRecordRequest) {
        WorkRecord workRecordList = workRecordRepository.findByIdemployeeAndMonthAndYear(workRecordRequest.getIdemployee(),workRecordRequest.getMonth(),workRecordRequest.getYear());
        if(workRecordList == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Không tìm thấy nhân viên, ngày hoặc tháng tương ứng trong bảng")
                    .build());
        }
       WorkRecordResponse workRecordResponse =    new WorkRecordResponse(
               workRecordList.getIdemployee(),
               workRecordList.getMonth(),
               workRecordList.getYear(),
               workRecordList.getDay_work());


        return ResponseEntity.ok().body(ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Lấy thành công bảng ghi công")
                .data(workRecordResponse).build());
    }

    @Override
    public WorkRecordResponse getWorkRecordMany(WorkRecordRequest workRecordRequest) {
        WorkRecord workRecordList = workRecordRepository.findByIdemployeeAndMonthAndYear(workRecordRequest.getIdemployee(),workRecordRequest.getMonth(),workRecordRequest.getYear());
        if(workRecordList == null){
         return null;
        }
        return new WorkRecordResponse(
                workRecordList.getIdemployee(),
                workRecordList.getMonth(),
                workRecordList.getYear(),
                workRecordList.getDay_work());
    }

    @Override
    public ResponseEntity<?> getIdemployee() {
        ResponseEntity<?> response = idEmployeeServiceClient.getEmployees();
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            LinkedHashMap employeeResponse = (LinkedHashMap) response.getBody();
            List employee = (List) employeeResponse.get("data");
            List<IdEmployeeClient>idEmployeeClientList = new ArrayList<>();
            for(int i = 0 ; i < employee.size();i++){
                LinkedHashMap linkedHashMap = (LinkedHashMap) employee.get(i);
                IdEmployeeClient idEmployeeClient = new IdEmployeeClient();
                idEmployeeClient.setIdemployee(linkedHashMap.get("idEmployee").toString());
                idEmployeeClientList.add(idEmployeeClient);
            }
            idEmployeeClientList = idEmployeeClientList.stream().filter(
                    e -> contractServiceClient.checkContractById(e.getIdemployee(), new Date())
            ).toList();

            return ResponseEntity.ok().body(idEmployeeClientList);
        }
        return null;
    }
}
