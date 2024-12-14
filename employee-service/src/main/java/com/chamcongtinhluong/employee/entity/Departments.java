package com.chamcongtinhluong.employee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "department")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Departments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddepartment")
    private int idDepartment;

    @Column(name = "departmentname",length = 100)
    private String departmentName;

    @Column(name = "departmentcode",length = 45)
    private String departmentCode;

    @OneToMany(mappedBy = "departments",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Employee> employees;
}
