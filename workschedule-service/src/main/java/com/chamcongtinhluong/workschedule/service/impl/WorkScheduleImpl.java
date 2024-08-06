package com.chamcongtinhluong.workschedule.service.impl;


import com.chamcongtinhluong.workschedule.commiunicate.ContractServiceClient;
import com.chamcongtinhluong.workschedule.commiunicate.EmployeeClient;
import com.chamcongtinhluong.workschedule.commiunicate.EmployeeServiceClient;
import com.chamcongtinhluong.workschedule.commiunicate.IDEmployeeClient;
import com.chamcongtinhluong.workschedule.dto.ApiResponse;
import com.chamcongtinhluong.workschedule.dto.DateRequest;
import com.chamcongtinhluong.workschedule.dto.WorkScheduleDetailRequest;
import com.chamcongtinhluong.workschedule.entity.EmployeeWorkScheduleId;
import com.chamcongtinhluong.workschedule.entity.WorkSchedule;
import com.chamcongtinhluong.workschedule.entity.WorkScheduleDetails;
import com.chamcongtinhluong.workschedule.repository.WorkScheduleDetailsRepository;
import com.chamcongtinhluong.workschedule.service.WorkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.chamcongtinhluong.workschedule.repository.WorkScheduleRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class WorkScheduleImpl implements WorkScheduleService {
    @Autowired
    private WorkScheduleRepository workScheduleRepository;

    @Autowired
    private EmployeeServiceClient employeeServiceClient;

    @Autowired
    private WorkScheduleDetailsRepository workScheduleDetailsRepository;

    @Autowired
    private ContractServiceClient contractServiceClient;

    @Override
    public ResponseEntity<?> getWorkSchedule() {
        return ResponseEntity.ok().body(new ApiResponse(HttpStatus.OK.value(),"Get work schedule success",workScheduleRepository.findAll()));
    }

    @Override
    public ResponseEntity<?> addWorkSchedule(WorkSchedule workSchedule) {
        Boolean isCheckExistDate = workScheduleRepository.existsByWorkdate(workSchedule.getWorkdate());
        if(isCheckExistDate){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("Ngày làm việc đã tồn tại").data("").build());
        }
       workScheduleRepository.save(workSchedule);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.builder().status(HttpStatus.CREATED.value()).message("Thêm lịch làm việc thành công").data(workSchedule).build());
    }

    @Override
    public ResponseEntity<?> getEmp(String id) {
        ResponseEntity<?> response = employeeServiceClient.getIDEmployee(id);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
           LinkedHashMap employeeResponse = (LinkedHashMap) response.getBody();
            LinkedHashMap employee = (LinkedHashMap) employeeResponse.get("data");
            EmployeeClient employeeClient = new EmployeeClient();
            employeeClient.setIdemployee(employee.get("idemployee").toString());
            employeeClient.setFirstname(employee.get("firstname").toString());
            employeeClient.setLastname(employee.get("lastname").toString());
            return ResponseEntity.ok().body(employeeClient);
    }
        return ResponseEntity.ok().body(null);
    }

    @Override
    public ResponseEntity<?> getIDEmp(Date date) {
        ResponseEntity<?> response = employeeServiceClient.getEmployees();
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            LinkedHashMap employeeResponse = (LinkedHashMap) response.getBody();
            List employee = (List) employeeResponse.get("data");
            List<IDEmployeeClient>idEmployeeClientList = new ArrayList<>();
            for(int i = 0 ; i < employee.size();i++){
                LinkedHashMap linkedHashMap = (LinkedHashMap) employee.get(i);
                IDEmployeeClient idEmployeeClient = new IDEmployeeClient();
                idEmployeeClient.setId(linkedHashMap.get("idemployee").toString());
                idEmployeeClientList.add(idEmployeeClient);
            }

            idEmployeeClientList = idEmployeeClientList.stream().filter(
                    e -> contractServiceClient.checkContractById(e.getId(), date)
            ).toList();

            return ResponseEntity.ok().body(idEmployeeClientList);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> addWorkScheduleEmployee(WorkScheduleDetailRequest workScheduleDetailRequest) {
        WorkSchedule workSchedule = workScheduleRepository.findByWorkdate(workScheduleDetailRequest.getWorkdate());
        if(workSchedule == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("Lịch làm việc không tồn tại").data("").build());
        }

        WorkScheduleDetails workScheduleDetails = new WorkScheduleDetails();
        workScheduleDetails.setEmployeeWorkScheduleId(new EmployeeWorkScheduleId(workSchedule.getIdwork_schedule(),workScheduleDetailRequest.getIdemployee()));
        workScheduleDetails.setWorkSchedule(workSchedule);
        List<WorkScheduleDetails> isExits = workScheduleDetailsRepository.findAll().stream().filter(
                e->(e.getEmployeeWorkScheduleId().getWorkschedule() == workScheduleDetails.getEmployeeWorkScheduleId().getWorkschedule() &&
                        e.getEmployeeWorkScheduleId().getIdemployee().equals(workScheduleDetails.getEmployeeWorkScheduleId().getIdemployee()))).toList();
        if(isExits.size()>0)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("Nhân viên đã có lịch làm việc").data("").build());
        }
        workScheduleDetailsRepository.save(workScheduleDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.builder().status(HttpStatus.CREATED.value()).message("Thêm ca nhân viên thành công").data("").build());
    }

    @Override
    public ResponseEntity<?> getEmployeeWorkSchedule(DateRequest date) {
        WorkSchedule workSchedule = workScheduleRepository.findByWorkdate(date.getDate());
        if(workSchedule == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder().status(HttpStatus.NOT_FOUND.value()).message("Không có lịch làm việc trong ngày này").data("").build());
        }
        List<WorkScheduleDetails> workScheduleDetailsList = workScheduleDetailsRepository.findAll().stream().filter(e->e.getEmployeeWorkScheduleId().getWorkschedule() == workSchedule.getIdwork_schedule()).toList();
        List<WorkScheduleDetailRequest> workScheduleDetailRequestList = workScheduleDetailsList.stream().map(e-> new WorkScheduleDetailRequest(e.getEmployeeWorkScheduleId().getIdemployee(),"",e.getWorkSchedule().getWorkdate(), e.getWorkSchedule().getStartime(), e.getWorkSchedule().getEndtime())).toList();
        for(WorkScheduleDetailRequest workScheduleDetailRequest: workScheduleDetailRequestList){
            ResponseEntity<?> response = employeeServiceClient.getIDEmployee(workScheduleDetailRequest.getIdemployee());
            LinkedHashMap employeeResponse = (LinkedHashMap) response.getBody();
            LinkedHashMap employee = (LinkedHashMap) employeeResponse.get("data");
            workScheduleDetailRequest.setName(employee.get("firstname").toString() + " " + employee.get("lastname").toString());
        }
        return ResponseEntity.ok().body(workScheduleDetailRequestList);
    }

    @Override
    public ResponseEntity<?> deleteWorkSchedule(Date date) {
        WorkSchedule workSchedule = workScheduleRepository.findByWorkdate(date);
        if(workSchedule == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.builder().status(HttpStatus.NOT_FOUND.value()).message("Không tồn tại ngày này").data("").build()
            );
        }

        List<WorkScheduleDetails> workScheduleDetailsList = workScheduleDetailsRepository.findAll()
                .stream().filter(e->e.getWorkSchedule().getWorkdate().equals(date)).toList();
        if(!workScheduleDetailsList.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("Không thể xóa lịch làm việc này do đã có nhân viên").data("").build()
            );
        }
        workScheduleRepository.delete(workSchedule);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder().status(HttpStatus.OK.value()).message("Xóa thành công lịch làm việc").data("").build()
        );
    }
}
