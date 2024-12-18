package com.chamcongtinhluong.contract_service.repository;

import com.chamcongtinhluong.contract_service.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract,Integer> {
//    Contract findByIdemployeeAndEndateAfterAndStartdateBefore(String idemployee, Date endate, Date startdate);

    List<Contract> findByIdemployee(String idemployee);

    List<Contract> findByIdemployeeAndStartdateBeforeAndEndateAfter(
            String idemployee, Date endate, Date startdate);

    Contract findByIdemployeeAndStartdateAndEndate(
            String idemployee, Date startdate, Date endate);


    List<Contract> findByIdemployeeAndStartdateAfter(
            String idemployee, Date startdate);

    List<Contract> findByIdemployeeAndEndateBefore(
            String idemployee, Date endate);

    @Query("SELECT c FROM Contract c WHERE c.idemployee = :idemployee AND :currentDate BETWEEN c.startdate AND c.endate AND c.status = 1")
    Contract findActiveContracts(String idemployee, Date currentDate);


    @Query("SELECT c " +
            "FROM Contract c " +
            "WHERE c.idemployee = :idemployee AND " +
            "( (YEAR(c.startdate) < :year OR (YEAR(c.startdate) = :year AND MONTH(c.startdate) <= :month)) AND " +
            "  (YEAR(c.endate) > :year OR (YEAR(c.endate) = :year AND MONTH(c.endate) >= :month)) ) " +
            "AND c.status = 1")
    Contract findContractByIdemployeeAndMonthAndYear(
            @Param("idemployee") String idemployee,
            @Param("month") int month,
            @Param("year") int year
    );

    @Query("SELECT COUNT(c) FROM Contract c WHERE " +
            "( (YEAR(c.startdate) < :year OR (YEAR(c.startdate) = :year AND MONTH(c.startdate) <= :month)) AND " +
            "  (YEAR(c.endate) > :year OR (YEAR(c.endate) = :year AND MONTH(c.endate) >= :month)) ) ")
    Integer countContractsByMonthAndYear(@Param("month") int month, @Param("year") int year);
}



