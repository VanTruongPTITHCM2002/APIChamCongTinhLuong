package com.chamcongtinhluong.employee.repository;

import com.chamcongtinhluong.employee.entity.Degree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DegreeRepository extends JpaRepository<Degree,Integer> {
    Degree findByDegreename(String degreeName);
}
