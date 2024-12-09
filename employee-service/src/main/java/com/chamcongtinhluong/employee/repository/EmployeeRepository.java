package com.chamcongtinhluong.employee.repository;

import com.chamcongtinhluong.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,String> {
    Employee findByIdemployee(String idemployee);
    int countByStatus(int status);
    Boolean existsByIdemployee(String idemployee);
    boolean existsByCmnd(String cmnd);
    boolean existsByEmail(String email);
    boolean existsByPhonenumber(String phoneNumber);
    @Query("SELECT e.idemployee FROM Employee e WHERE e.departments.departmentName = :departments")
    List<String> findList(String departments);
}
