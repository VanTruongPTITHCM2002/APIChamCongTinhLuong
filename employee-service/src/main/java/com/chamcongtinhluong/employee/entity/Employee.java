package com.chamcongtinhluong.employee.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "employee")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "idemployee")
    private String idemployee;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "gender")
    private String gender;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "cmnd")
    private String cmnd;

    @Column(name = "email")
    private String email;

    @Column(name = "position")
    private String position;

    @Column(name = "phonenumber")
    private String phonenumber;

    @Column(name="address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "iddegree")
    private Degree degree;

    @ManyToOne
    @JoinColumn(name = "iddepartment")
    private Departments departments;


    @Lob
    @Column(name = "image", columnDefinition = "MEDIUMBLOB", nullable = false)
    private byte[] image;

    @Column(name="status")
    private int status;
}
