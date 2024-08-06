package com.chamcongtinhluong.employee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="degree")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Degree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iddegree;

    @OneToMany(mappedBy = "degree",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Employee> employeeList;

    @Column(name = "degreename")
    private String degreename;

    @Column(name="number_sal")
    private int number_sal;
}
