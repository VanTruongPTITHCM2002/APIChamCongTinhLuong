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
    public ResponseEntity<?> getAllDepartmens() {
        try{
            List<DepartmentsDTO> departmentsDTOList =
                    departmentsRepository.findAll().stream()
                            .map(departments ->
                                    DepartmentsDTO.builder()
                                            .departmentsName(departments.getDepartmentName())
                                            .departmentCode(departments.getDepartmentCode())
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
                    ResponeObject.builder()
                            .status(HttpStatus.OK.value())
                            .message("Da tim thay phong ban cua nhan vien")
                            .data(employee.getDepartments().getDepartmentName())
                            .build()

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

    @Override
    public ResponseEntity<?> addDepartment(DepartmentsDTO departmentsDTO) {
        Departments departments = departmentsRepository.findByDepartmentCode(departmentsDTO.getDepartmentCode());
        if(departments != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponeObject.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Phòng ban nay đã ton tai")
                    .build());
        }
        departments = new Departments();
        departments.setDepartmentName(departmentsDTO.getDepartmentsName());
        departments.setDepartmentCode(departments.getDepartmentCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponeObject.builder()
                .status(HttpStatus.CREATED.value())
                .message("Tao phong ban thanh cong")
                .build());
    }

    @Override
    public ResponseEntity<?> updateDepartment(String departmentCode, DepartmentsDTO departmentsDTO) {
        Departments departments = departmentsRepository.findByDepartmentCode(departmentCode);
        if(departments == null){
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponeObject.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Khong tim thay phong ban nay")
                    .build());
        }
        departments.setDepartmentCode(departmentsDTO.getDepartmentCode());
        departments.setDepartmentName(departmentsDTO.getDepartmentsName());
        departmentsRepository.save(departments);
        return ResponseEntity.ok().body(ResponeObject.builder()
                .status(HttpStatus.OK.value())
                .message("Sua thanh cong phong ban nay")
                .build());
    }

    @Override
    public ResponseEntity<?> deleteDepartment(String departmentCode) {
        Departments departments = departmentsRepository.findByDepartmentCode(departmentCode);
        if(departments == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponeObject.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Khong tim thay phong ban nay")
                    .build());
        }
        if(!departments.getEmployees().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponeObject.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Khong the xoa phong ban nay")
                    .build());
        }
        departmentsRepository.delete(departments);
        return ResponseEntity.ok().body(ResponeObject.builder()
                .status(HttpStatus.OK.value())
                .message("Xoa thanh cong phong ban nay")
                .build());
    }
}
