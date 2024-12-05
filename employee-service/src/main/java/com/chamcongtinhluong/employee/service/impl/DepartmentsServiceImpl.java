package com.chamcongtinhluong.employee.service.impl;

import com.chamcongtinhluong.employee.dto.DepartmentsDTO;
import com.chamcongtinhluong.employee.dto.response.ResponeObject;
import com.chamcongtinhluong.employee.entity.Departments;
import com.chamcongtinhluong.employee.entity.Employee;
import com.chamcongtinhluong.employee.repository.DepartmentsRepository;
import com.chamcongtinhluong.employee.repository.EmployeeRepository;
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
    private final EmployeeRepository employeeRepository;

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

    @Override
    public ResponseEntity<?> getDepartmentNameByIdEmployee(String idEmployee) {
        try{
            Employee employee = employeeRepository.findByIdemployee(idEmployee);
            return ResponseEntity.ok().body(
                    employee.getDepartments().getDepartmentName()
            );
        }catch (NullPointerException nullPointerException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            ResponeObject.builder()
                                    .status(HttpStatus.NOT_FOUND.value())
                                    .message("Khong tim thay nhan vien nay")
                                    .build()
                    );
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponeObject.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Loi ket noi den co so du lieu")
                            .build());
        }
    }
}
