package com.chamcongtinhluong.deduction_service.repository;

import com.chamcongtinhluong.deduction_service.entity.Deduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeductionRepository extends JpaRepository<Deduction,Integer> {
}
