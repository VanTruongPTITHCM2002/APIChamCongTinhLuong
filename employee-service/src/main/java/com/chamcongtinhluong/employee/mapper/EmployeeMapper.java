package com.chamcongtinhluong.employee.mapper;

import com.chamcongtinhluong.employee.dto.request.EmployeeRequest;
import com.chamcongtinhluong.employee.dto.response.EmployeeResponse;
import com.chamcongtinhluong.employee.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(source = "idEmployee",target = "idemployee")
    @Mapping(source = "firstName", target = "firstname")
    @Mapping(source = "lastName", target = "lastname")
    @Mapping(source = "birthDate", target = "birthdate")
    @Mapping(source = "idCard", target = "cmnd")
    @Mapping(source = "email",target = "email")
    @Mapping(source = "phoneNumber", target = "phonenumber")
    @Mapping(target = "degree", ignore = true) // Tùy chỉnh thêm
    @Mapping(target = "departments", ignore = true)
    @Mapping(source = "position",target= "position")
    @Mapping(source = "image",target = "image")
    @Mapping(target = "status", expression = "java(mapStatus(employeeRequest.getStatus()))")
    Employee toEntity(EmployeeRequest employeeRequest);


    @Mapping(source = "idemployee", target = "idEmployee")
    @Mapping(source = "firstname", target = "firstName")
    @Mapping(source = "lastname", target = "lastName")
    @Mapping(source = "birthdate", target = "birthDate")
    @Mapping(source = "cmnd", target = "idCard")
    @Mapping(source = "email",target = "email")
    @Mapping(source = "phonenumber", target = "phoneNumber")
    @Mapping(source = "degree.degreename", target = "degree")
    @Mapping(source = "position",target= "position")
    @Mapping(source = "image",target = "image")// Mapping tên của Degree
    @Mapping(source = "departments.departmentName", target = "department") // Mapping tên của Department
    @Mapping(target = "status", expression = "java(mapStatus(employee.getStatus()))")
    EmployeeResponse toResponse(Employee employee);

    @Mapping(target = "idemployee", ignore = true) // bỏ qua trường id để không ghi đè
    @Mapping(target = "degree", ignore = true) // Tùy chỉnh thêm
    @Mapping(target = "departments", ignore = true)
    void updateEmployeeFromDto(EmployeeRequest e, @MappingTarget Employee emp);


    default String mapStatus(int status) {
        return status == 1 ? "Đang hoạt động" : "Ngưng hoạt động";
    }


    default int mapStatus(String status) {
        return status != null && status.equalsIgnoreCase("Đang hoạt động") ? 1 : 0;
    }
}
