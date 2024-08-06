package com.chamcongtinhluong.employee.repository;

import com.chamcongtinhluong.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,String> {
    Employee findByIdemployee(String idemployee);
    int countByStatus(int status);
}
