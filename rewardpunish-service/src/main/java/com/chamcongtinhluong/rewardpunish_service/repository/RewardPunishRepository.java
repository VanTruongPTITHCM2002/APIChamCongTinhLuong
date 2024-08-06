package com.chamcongtinhluong.rewardpunish_service.repository;

import com.chamcongtinhluong.rewardpunish_service.entity.RewardPunish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RewardPunishRepository extends JpaRepository<RewardPunish,Integer> {
    RewardPunish findByIdemployeeAndReasonAndSetupdate(String idemployee, String reason, Date setupdate);


    @Query("SELECT rp " +
            "FROM RewardPunish rp " +
            "WHERE rp.idemployee = :idemployee AND " +
            "MONTH(rp.setupdate) = :month AND YEAR(rp.setupdate) = :year AND " +
            "rp.status = 1")
    List<RewardPunish> findByIdemployeeAndMonthAndYear(
            @Param("idemployee") String idemployee,
            @Param("month") int month,
            @Param("year") int year
    );
    @Query("SELECT rp FROM RewardPunish rp WHERE rp.status = 1 AND YEAR(rp.setupdate) = :year")
    List<RewardPunish> findByYearWithStatus(int year);
}
