package com.chamcongtinhluong.employee.service.impl;

import com.chamcongtinhluong.employee.Enum.DegreeNumber;
import com.chamcongtinhluong.employee.Enum.StatusEmployee;
import com.chamcongtinhluong.employee.communicate.AccountServiceClient;
import com.chamcongtinhluong.employee.communicate.ContractEmployeeServiceClient;
import com.chamcongtinhluong.employee.communicate.CreateAccountRequest;
import com.chamcongtinhluong.employee.dto.EmployeeDTO;
import com.chamcongtinhluong.employee.dto.response.DetailSalaryResponse;
import com.chamcongtinhluong.employee.entity.Degree;
import com.chamcongtinhluong.employee.entity.Employee;
import com.chamcongtinhluong.employee.exception.InvalidEmployeeException;

import com.chamcongtinhluong.employee.repository.DegreeRepository;
import com.chamcongtinhluong.employee.repository.EmployeeRepository;
import com.chamcongtinhluong.employee.respone.ResponeObject;
import com.chamcongtinhluong.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DegreeRepository degreeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AccountServiceClient accountServiceClient;

    @Autowired
    private ContractEmployeeServiceClient contractEmployeeServiceClient;

    @Override
    public String generateEmployeeId() {
        String sql = "SELECT MAX(idemployee) FROM employee";
        String maxId = jdbcTemplate.queryForObject(sql, String.class);

        if (maxId == null || maxId.isEmpty()) {
            return "NV001";
        }

        int numericPart = Integer.parseInt(maxId.substring(2));
        String newId = String.format("NV%03d", numericPart + 1);
        return newId;
    }

    @Override
    public String getDetailSalary(String idemployee) {
        Employee employee = employeeRepository.findByIdemployee(idemployee);
        return employee.getFirstname() + ' ' + employee.getLastname();
    }

    @Override
    public ResponseEntity<?> getEmployeeActive() {
        List<EmployeeDTO> employeeDTOList = employeeRepository.findAll().stream().filter(e->e.getStatus() == 1 && !e.getIdemployee().equals("NV001")).map(
                e-> new EmployeeDTO(
                        e.getIdemployee(),
                        e.getFirstname(),
                        e.getLastname(),
                        e.getGender(),
                        e.getBirthdate(),
                        e.getCmnd(),
                        e.getEmail(),
                        e.getPhonenumber(),
                        e.getAddress(),
                        DegreeNumber.getStatusFromCode(e.getDegree().getIddegree()),
                        StatusEmployee.getStatusFromCode(e.getStatus())

                )
        ).toList();
        return ResponseEntity.ok().body(new ResponeObject(HttpStatus.OK.value(),"Danh sách nhân viên",employeeDTOList));

    }

    @Override
    public ResponseEntity<?> countEmployee() {
        int amount = employeeRepository.countByStatus(1);

        return ResponseEntity.ok().body(new ResponeObject(HttpStatus.OK.value(), "Số lượng nhân viên",amount));
    }

    @Override
    public ResponseEntity<?> getEmployee() {
        List<EmployeeDTO> employeeDTOList = employeeRepository.findAll().stream()
                .filter(e->!e.getIdemployee().equals("NV001"))
                .map(
                e-> new EmployeeDTO(
                        e.getIdemployee(),
                        e.getFirstname(),
                        e.getLastname(),
                        e.getGender(),
                        e.getBirthdate(),
                        e.getCmnd(),
                        e.getEmail(),
                        e.getPhonenumber(),
                        e.getAddress(),
                       DegreeNumber.getStatusFromCode(e.getDegree().getIddegree()),
                        StatusEmployee.getStatusFromCode(e.getStatus())

                )
        ).toList();
        return ResponseEntity.ok().body(new ResponeObject(HttpStatus.OK.value(),"Danh sách nhân viên",employeeDTOList));
    }

    @Override
    public ResponseEntity<?> getIDEmployee(String id) {
        Employee emp = employeeRepository.findByIdemployee(id);
        if(emp == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponeObject(HttpStatus.NOT_FOUND.value(), "Not found employee"+ id,""));
        }
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setIdemployee(emp.getIdemployee());
        employeeDTO.setFirstname(emp.getFirstname());
        employeeDTO.setLastname(emp.getLastname());
        employeeDTO.setGender(emp.getGender());
        employeeDTO.setEmail(emp.getEmail());
        employeeDTO.setBirthdate(emp.getBirthdate());
        employeeDTO.setAddress(emp.getAddress());
        employeeDTO.setStatus(StatusEmployee.getStatusFromCode(emp.getStatus()));
        employeeDTO.setPhonenumber(emp.getPhonenumber());
        employeeDTO.setCmnd(emp.getCmnd());
        employeeDTO.setDegree(DegreeNumber.getStatusFromCode(emp.getDegree().getIddegree()));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponeObject(HttpStatus.OK.value(), "Get employee successs "+ id,employeeDTO));
    }

    @Override
    public ResponseEntity<?> addEmployee(EmployeeDTO e) {
        if (e.getLastname() == null || e.getLastname().isEmpty()) {
            throw new InvalidEmployeeException("Employee name is required");
        }
        Employee emp = new Employee();
        Degree degree = degreeRepository.findById(DegreeNumber.getCodeFromStatus(e.getDegree())).orElse(null);

        emp.setIdemployee(e.getIdemployee());
        emp.setFirstname(e.getFirstname());
        emp.setLastname(e.getLastname());
        emp.setGender(e.getGender());
        emp.setEmail(e.getEmail());
        emp.setBirthdate(e.getBirthdate());
        emp.setAddress(e.getAddress());
        emp.setStatus(StatusEmployee.getCodeFromStatus(e.getStatus()));
        emp.setPhonenumber(e.getPhonenumber());
        emp.setCmnd(e.getCmnd());
        emp.setDegree(degree);

        employeeRepository.save(emp);
        accountServiceClient.createAccount(new CreateAccountRequest(e.getIdemployee(),"1",2));
        return ResponseEntity.ok().body(new ResponeObject(HttpStatus.CREATED.value(),"Thêm nhân viên thành công",e));
    }

    @Override
    public ResponseEntity<?> updateEmployee(String id, EmployeeDTO e) {
        Employee emp = employeeRepository.findByIdemployee(id);
        if(emp == null){
            return ResponseEntity.ok().body(new ResponeObject(HttpStatus.NOT_FOUND.value(), "Not found employee",""));
        }
        Degree degree = degreeRepository.findById(DegreeNumber.getCodeFromStatus(e.getDegree())).orElse(null);

        emp.setFirstname(e.getFirstname());
        emp.setLastname(e.getLastname());
        emp.setGender(e.getGender());
        emp.setCmnd(e.getCmnd());
        emp.setEmail(e.getEmail());
        emp.setPhonenumber(e.getPhonenumber());
        emp.setAddress(e.getAddress());
        emp.setDegree(degree);
        emp.setStatus(StatusEmployee.getCodeFromStatus(e.getStatus()));
        if(e.getStatus().equals("Ngưng hoạt động")){
            accountServiceClient.changeStatus(id);
        }
        employeeRepository.save(emp);
        return ResponseEntity.ok().body(new ResponeObject(HttpStatus.OK.value(), "Sửa nhân viên thành công",""));
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
        System.out.println(contractEmployeeServiceClient.checkEmployee(idemployee));
        if(contractEmployeeServiceClient.checkEmployee(idemployee)){
            return ResponseEntity.ok().body(
                    new ResponeObject(HttpStatus.BAD_REQUEST.value(), "Không thể xóa nhân viên này","")
            );
        }
        employeeRepository.delete(emp);
        accountServiceClient.deleteAccount(idemployee);
        return ResponseEntity.ok().body(new ResponeObject(HttpStatus.OK.value(), "Xóa nhân viên thành công",""));
    }

}
