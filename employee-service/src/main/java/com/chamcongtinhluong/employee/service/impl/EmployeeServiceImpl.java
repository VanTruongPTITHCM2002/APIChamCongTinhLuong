package com.chamcongtinhluong.employee.service.impl;


import com.chamcongtinhluong.employee.Enum.StatusEmployee;
import com.chamcongtinhluong.employee.communicate.AccountServiceClient;
import com.chamcongtinhluong.employee.communicate.ContractEmployeeServiceClient;
import com.chamcongtinhluong.employee.communicate.CreateAccountRequest;
import com.chamcongtinhluong.employee.dto.request.EmployeeRequest;
import com.chamcongtinhluong.employee.dto.response.EmployeeResponse;
import com.chamcongtinhluong.employee.entity.Employee;
import com.chamcongtinhluong.employee.mapper.EmployeeMapper;
import com.chamcongtinhluong.employee.repository.DegreeRepository;
import com.chamcongtinhluong.employee.repository.DepartmentsRepository;
import com.chamcongtinhluong.employee.repository.EmployeeRepository;
import com.chamcongtinhluong.employee.dto.response.ResponeObject;
import com.chamcongtinhluong.employee.service.EmployeeService;
import com.chamcongtinhluong.employee.utils.GenerateID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DegreeRepository degreeRepository;
    private final JdbcTemplate jdbcTemplate;
    private final AccountServiceClient accountServiceClient;
    private final ContractEmployeeServiceClient contractEmployeeServiceClient;
    private  final DepartmentsRepository departmentsRepository;
    private  final GenerateID generateID;

//

    @Override
    public String getDetailSalary(String idEmployee) {
        Employee employee = employeeRepository.findByIdemployee(idEmployee);
        return employee.getFirstname() + ' ' + employee.getLastname();
    }

    @Override
    public ResponseEntity<?> getEmployeeActive() {
        List<EmployeeResponse> employeeDTOList = employeeRepository.findAll().stream().filter(e->e.getStatus() == 1
            && contractEmployeeServiceClient.checkEmployee(e.getIdemployee())
        ).map(
                EmployeeMapper.INSTANCE::toResponse
        ).toList();
        return ResponseEntity.ok().body(new ResponeObject(HttpStatus.OK.value(),"Danh sách nhân viên",employeeDTOList));

    }

    @Override
    public ResponseEntity<?> countEmployee() {
        int amount = employeeRepository.countByStatus(1);

        return ResponseEntity.ok().body(new ResponeObject(HttpStatus.OK.value(), "Số lượng nhân viên",amount));
    }

    @Override
    public ResponseEntity<?> uploadFileEmployee(MultipartFile multipartFile, String idEmployee) throws IOException {
        try{
            byte [] imageBytes = multipartFile.getBytes();
            Employee employee = employeeRepository.findByIdemployee(idEmployee);
            employee.setImage(imageBytes);
            employeeRepository.save(employee);
        }catch (IOException ioException){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponeObject.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Loi tai anh")
                            .build());
        }
        return ResponseEntity.ok().body(
                ResponeObject.builder()
                        .status(HttpStatus.OK.value())
                        .message("Tai anh thanh cong")
                        .build()

        );
    }

    @Override
    public ResponseEntity<?> getImageEmployee(String idEmployee) {
        Employee employee = employeeRepository.findByIdemployee(idEmployee);
        byte[] image = employee.getImage();
        if (image == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Trả về file với đúng kiểu MIME (image/jpeg, image/png, v.v.)
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)  // Chỉnh lại loại MIME tùy theo loại ảnh
                .body(image);

    }

    @Override
    public int getNumberSalary(String idEmployee) {
        Employee employee = employeeRepository.findByIdemployee(idEmployee);
        return employee.getDegree().getNumber_sal();
    }

    @Override
    public ResponseEntity<?> getEmployee() {
        try{
            List<EmployeeResponse> employeeDTOList = employeeRepository.findAll().stream()
                    .map(
                            EmployeeMapper.INSTANCE::toResponse
                    ).toList();

            return ResponseEntity.ok().body(
            ResponeObject.builder()
                    .status(HttpStatus.OK.value())
                    .message("Danh sách tất cả nhân viên")
                    .data(employeeDTOList)
                    .build()
            );
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponeObject.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Lỗi kết nối cơ sở dữ liệu không thể lấy danh sách nhân viên")
                            .build());
        }

    }

    @Override
    public ResponseEntity<?> getEmployeeDepartments(String departments) {
        try{
            List<EmployeeResponse> employeeResponseList = employeeRepository
                    .findAll().stream()
                    .filter(e-> e.getDepartments().getDepartmentName().equals(departments))
                    .map(
                            EmployeeMapper.INSTANCE::toResponse
                    ).toList();

            return ResponseEntity.ok().body(
                    ResponeObject.builder()
                            .status(HttpStatus.OK.value())
                            .message("Lay thanh cong danh sach nhan vien phong " + departments)
                            .data(employeeResponseList)
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
    public ResponseEntity<?> getIDEmployee(String id) {
        Employee emp = employeeRepository.findByIdemployee(id);
        if(emp == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponeObject(HttpStatus.NOT_FOUND.value(), "Not found employee"+ id,""));
        }
        EmployeeResponse employeeResponse = EmployeeMapper.INSTANCE.toResponse(emp);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponeObject(HttpStatus.OK.value(), "Get employee successs "+ id,employeeResponse));
    }

    @Override
    public ResponseEntity<?> addEmployee(EmployeeRequest e) {

        try{
            Employee employee = EmployeeMapper.INSTANCE.toEntity(e);
            employee.setIdemployee(generateID.generateIdEmoloyee(e.getDepartment()));

            employee.setDegree(degreeRepository.findByDegreename(e.getDegree()));
            employee.setDepartments(departmentsRepository.findByDepartmentName(e.getDepartment()));
            employeeRepository.save(employee);

            accountServiceClient.createAccount(new CreateAccountRequest(employee.getIdemployee(),"1",2));
            return ResponseEntity.status(
                    HttpStatus.CREATED
            ).body(ResponeObject
                    .builder()
                    .status(HttpStatus.CREATED.value())
                    .message("Thêm nhân viên thành công")
                            .data(employee.getIdemployee())
                    .build()
            );
        }catch (Exception exception){
            System.out.println(exception.getMessage());
                 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                         .body(ResponeObject.builder()
                                 .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                 .message("Lỗi kết nối cơ sở dữ liệu không thể thêm nhân viên")
                                 .build());
        }



    }

    @Override
    public ResponseEntity<?> updateEmployee(String id, EmployeeRequest e) {
        try{
            Employee emp = employeeRepository.findByIdemployee(id);
            EmployeeMapper.INSTANCE.updateEmployeeFromDto(e,emp);
            emp.setDegree(degreeRepository.findByDegreename(e.getDegree()));
            emp.setDepartments(departmentsRepository.findByDepartmentName(e.getDepartment()));
            emp.setStatus(StatusEmployee.getCodeFromStatus(e.getStatus()));
            if(e.getStatus().equals("Ngưng hoạt động")){
                accountServiceClient.changeStatus(id);
            }
            employeeRepository.save(emp);
        }catch (NullPointerException nullPointerException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponeObject.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("Không thể tìm thấy nhân viên có mã nhân viên " + id)
                            .build()
                           );
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponeObject.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Lỗi kết nối cơ sở dữ liệu không thể cập nhật nhân viên")
                            .build());
        }
        return ResponseEntity.ok().body(ResponeObject
                .builder()
                        .status(HttpStatus.OK.value())
                        .message("Cập nhật thông tin nhân viên thành công")
                .build()
              );
    }

    @Override
    public ResponseEntity<?> updateStatusEmployee(String idemployee) {
        Employee employee = employeeRepository.findByIdemployee(idemployee);
        if(employee == null){
            return ResponseEntity.ok().body(
                    new ResponeObject(HttpStatus.NOT_FOUND.value(), "Không tìm thấy nhân viên","")
            );
        }
        employee.setStatus(0);
        employeeRepository.save(employee);
        accountServiceClient.changeStatus(idemployee);
        return ResponseEntity.ok().body(
                new ResponeObject(HttpStatus.OK.value(), "Thay đổi trạng thái thành công","")
        );
    }

    @Override
    public ResponseEntity<?> deleteEmployee(String idemployee) {
        Employee emp = employeeRepository.findByIdemployee(idemployee);
        if(emp == null){
            return ResponseEntity.ok().body(new ResponeObject(HttpStatus.NOT_FOUND.value(), "Không tìm thấy nhân viên",""));
        }
//        System.out.println(contractEmployeeServiceClient.checkEmployee(idemployee));

        try{
                        if(contractEmployeeServiceClient.checkEmployee(idemployee)){
                return ResponseEntity.ok().body(
                        new ResponeObject(HttpStatus.BAD_REQUEST.value(), "Không thể xóa nhân viên này","")
                );
            }

           //log.info("hihihi",accountServiceClient.deleteAccount(idemployee).getStatusCode());
          accountServiceClient.deleteAccount(idemployee);

            employeeRepository.delete(emp);
            return ResponseEntity.ok().body(new ResponeObject(HttpStatus.OK.value(), "Xóa nhân viên thành công",""));

        }catch (Exception connectException){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponeObject(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Lỗi kết nối không thể thực hiện xoa","")
            );

        }

//        employeeRepository.delete(emp);
//        accountServiceClient.deleteAccount(idemployee);
//        return ResponseEntity.ok().body(new ResponeObject(HttpStatus.OK.value(), "Xóa nhân viên thành công",""));
    }

}
