package com.chamcongtinhluong.employee.service.impl;

import com.chamcongtinhluong.employee.dto.DepartmentsDTO;
import com.chamcongtinhluong.employee.dto.response.ResponeObject;
import com.chamcongtinhluong.employee.repository.DepartmentsRepository;
import com.chamcongtinhluong.employee.service.DepartmentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentsServiceImpl implements DepartmentsService {

    private final DepartmentsRepository departmentsRepository;

    @Override
    public ResponseEntity<?> getDepartments() {
        try{
            List<DepartmentsDTO> departmentsDTOList =
                    departmentsRepository.findAll().stream()
                            .map(departments ->
                                    DepartmentsDTO.builder()
                                            .departmentsName(departments.getDepartmentName())
                                            .build()
                            ).toList();
            return ResponseEntity.ok().body(
                    ResponeObject.builder()
                            .status(HttpStatus.OK.value())
                            .message("Lay thanh cong danh sach phong ban")
                            .data(departmentsDTOList)
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponeObject.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Loi ket noi co so du lieu...")
                            .build());
        }
    }
}
