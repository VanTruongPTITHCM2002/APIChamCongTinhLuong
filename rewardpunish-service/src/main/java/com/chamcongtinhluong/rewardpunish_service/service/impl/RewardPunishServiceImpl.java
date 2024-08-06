package com.chamcongtinhluong.rewardpunish_service.service.impl;


import com.chamcongtinhluong.rewardpunish_service.Enum.StatusRewardPunish;
import com.chamcongtinhluong.rewardpunish_service.Enum.TypeRewardPunish;
import com.chamcongtinhluong.rewardpunish_service.commiunicate.WorkScheduleServiceClient;
import com.chamcongtinhluong.rewardpunish_service.dto.request.RewardPunishPayrollRequest;
import com.chamcongtinhluong.rewardpunish_service.dto.response.ApiResponse;
import com.chamcongtinhluong.rewardpunish_service.dto.response.RewardPunishPayrollResponse;
import com.chamcongtinhluong.rewardpunish_service.dto.response.RewardPunishResponse;
import com.chamcongtinhluong.rewardpunish_service.entity.RewardPunish;
import com.chamcongtinhluong.rewardpunish_service.repository.RewardPunishRepository;
import com.chamcongtinhluong.rewardpunish_service.service.RewardPunishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class RewardPunishServiceImpl implements RewardPunishService {

    @Autowired
    private RewardPunishRepository rewardPunishRepository;

    @Autowired
    private WorkScheduleServiceClient workScheduleServiceClient;

    @Override
    public ResponseEntity<?> getRewardPunish() {
        List<RewardPunishResponse> rewardPunishResponseList = rewardPunishRepository.findAll().stream().
                filter(e-> e.getStatus() != -1).
        map(rewardPunish -> {
          return RewardPunishResponse.builder().idemployee(rewardPunish.getIdemployee())
                   .reason(rewardPunish.getReason())
                   .cash(rewardPunish.getCash())
                   .type(TypeRewardPunish.getStatusFromCode(rewardPunish.getType()))
                   .setupdate(rewardPunish.getSetupdate())
                  .status(StatusRewardPunish.getStatusFromCode(rewardPunish.getStatus())).build();

        } ).toList();
        return ResponseEntity.ok().body(ApiResponse
                .builder().status(HttpStatus.OK.value())
                .message("Lấy thành công bảng thưởng phạt")
                .data(rewardPunishResponseList)
                .build());
    }

    @Override
    public ResponseEntity<?> getRewardPunishByIdemployee(String idemployee) {
        List<RewardPunishResponse> rewardPunishResponseList = rewardPunishRepository.findAll()
                .stream().filter(rewardPunish -> rewardPunish.getIdemployee().equals(idemployee)
                    && rewardPunish.getStatus() != -1
                )
                .map(rewardPunish -> {
                    return RewardPunishResponse.builder().idemployee(rewardPunish.getIdemployee())
                            .reason(rewardPunish.getReason())
                            .cash(rewardPunish.getCash())
                            .type(TypeRewardPunish.getStatusFromCode(rewardPunish.getType()))
                            .setupdate(rewardPunish.getSetupdate())
                            .status(StatusRewardPunish.getStatusFromCode(rewardPunish.getStatus())).build();

                }).toList();
        return ResponseEntity.ok().body(ApiResponse
                .builder().status(HttpStatus.OK.value())
                .message("Lấy thành công bảng thưởng phạt của " + idemployee)
                .data(rewardPunishResponseList)
                .build());
    }

    @Override
    public ResponseEntity<?> addRewardPunish( RewardPunishResponse rewardPunishResponse) {

        Boolean isCheck = workScheduleServiceClient.getWorkScheduleDetailByIdAndDate(rewardPunishResponse.getIdemployee(),rewardPunishResponse.getSetupdate());
        if(!isCheck)
        {
            return ResponseEntity.ok().body(
                    ApiResponse.builder().status(HttpStatus.NOT_FOUND.value())
                            .message("Không thể thêm do nhân viên không có lịch trong ngày này")
                            .data(rewardPunishResponse).build()
            );
        }
        rewardPunishRepository.save(RewardPunish.builder()
                .idemployee(rewardPunishResponse.getIdemployee())
                .type(TypeRewardPunish.getCodeFromStatus(rewardPunishResponse.getType()))
                .cash(rewardPunishResponse.getCash())
                .setupdate(rewardPunishResponse.getSetupdate())
                .reason(rewardPunishResponse.getReason()).
                status(StatusRewardPunish.getCodeFromStatus(rewardPunishResponse.getStatus())).build());

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder().status(HttpStatus.CREATED.value())
                        .message("Thêm thành công bảng" + rewardPunishResponse.getType())
                        .data(rewardPunishResponse).build()
        );
    }

    @Override
    public ResponseEntity<?> deleteRewardPunish(RewardPunishResponse rewardPunishResponse) {

         RewardPunish rewardPunish = rewardPunishRepository.findByIdemployeeAndReasonAndSetupdate(
                rewardPunishResponse.getIdemployee(),
             rewardPunishResponse.getReason(),
              rewardPunishResponse.getSetupdate()
        );
         rewardPunish.setStatus(StatusRewardPunish.getCodeFromStatus(rewardPunishResponse.getStatus()));
         rewardPunishRepository.save(rewardPunish);
        return ResponseEntity.ok().body(
                ApiResponse.builder().status(HttpStatus.OK.value())
                        .message("Xóa thành công")
                        .data("")
                        .build()
        );
    }

    @Override
    public List<RewardPunishPayrollResponse> calculateSalary(RewardPunishPayrollRequest rewardPunishPayrollRequest) {
        List<RewardPunishPayrollResponse> rewardPunishPayrollResponseList = rewardPunishRepository
                .findByIdemployeeAndMonthAndYear(rewardPunishPayrollRequest.getIdemployee(), rewardPunishPayrollRequest.getMonth(), rewardPunishPayrollRequest.getYear()).
                stream().map(r->RewardPunishPayrollResponse.builder().
                        idemployee(r.getIdemployee())
                        .type(TypeRewardPunish.getStatusFromCode(r.getType()))
                        .cash(r.getCash())
                .build()).toList();
        if(rewardPunishPayrollResponseList.isEmpty()){
            return rewardPunishPayrollResponseList;
        }
        return rewardPunishPayrollResponseList;
    }

    @Override
    public List<List<Integer>> getMonthlyCashTotalsByYearAndType(int year) {
        List<RewardPunish> rewardPunishList = rewardPunishRepository.findByYearWithStatus(year);
        List<Integer> monthlyRewards = new ArrayList<>(12);
        List<Integer> monthlyPenalties = new ArrayList<>(12);

        // Initialize the lists with zeros for 12 months
        for (int i = 0; i < 12; i++) {
            monthlyRewards.add(0);
            monthlyPenalties.add(0);
        }

        for (RewardPunish rp : rewardPunishList) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(rp.getSetupdate());
            int month = cal.get(Calendar.MONTH); // Months are 0-indexed (January is 0, February is 1, etc.)

            if (rp.getType() == 1) { // Reward
                int currentTotal = monthlyRewards.get(month);
                monthlyRewards.set(month, currentTotal + rp.getCash());
            } else if (rp.getType() == -1) { // Penalty
                int currentTotal = monthlyPenalties.get(month);
                monthlyPenalties.set(month, currentTotal + rp.getCash());
            }
        }

        List<List<Integer>> result = new ArrayList<>();
        result.add(monthlyRewards);
        result.add(monthlyPenalties);
        return result;
    }


}
