package com.chamcongtinhluong.payroll_service.repository;

import com.chamcongtinhluong.payroll_service.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll,Integer> {
    Payroll findByIdemployeeAndMonthAndYear(String idemployee, int month, int year);
    Payroll findByIdemployee(String idemployee);
    @Query("SELECT SUM(p.totalpayment) FROM Payroll p WHERE p.month = :month AND p.year = :year")
    Integer getTotalPaymentByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT p FROM Payroll p WHERE p.year = :year AND p.idemployee = :idemployee")
    List<Payroll> findByYearAndEmployee(@Param("year") int year, @Param("idemployee") String idemployee);
}
