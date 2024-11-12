package com.chamcongtinhluong.employee.repository;

import com.chamcongtinhluong.employee.entity.Departments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentsRepository extends JpaRepository<Departments,Integer> {
    Departments findByDepartmentName(String departmentName);
}
