package com.chamcongtinhluong.rewardpunish_service.controller;

import com.chamcongtinhluong.rewardpunish_service.dto.request.RewardPunishPayrollRequest;
import com.chamcongtinhluong.rewardpunish_service.dto.response.RewardPunishPayrollResponse;
import com.chamcongtinhluong.rewardpunish_service.dto.response.RewardPunishResponse;
import com.chamcongtinhluong.rewardpunish_service.entity.RewardPunish;
import com.chamcongtinhluong.rewardpunish_service.service.RewardPunishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/rewardpunish")
public class RewardPunishController {

    @Autowired
    private RewardPunishService rewardPunishService;

    @GetMapping
    private ResponseEntity<?>getRewardPunish(){
        return rewardPunishService.getRewardPunish();
    }

    @GetMapping("/{idemployee}")
    private ResponseEntity<?> getRewardPunishById(@PathVariable String idemployee){
        return rewardPunishService.getRewardPunishByIdemployee(idemployee);
    }

    @GetMapping("/countRewardPunish")
    private List<List<Integer>> getYearRewardPunish(@RequestParam int year){
        return rewardPunishService.getMonthlyCashTotalsByYearAndType(year);
    }
    @PostMapping("/calsalary")
    private List<RewardPunishPayrollResponse> getRewardPunishForCalSalary(@RequestBody RewardPunishPayrollRequest rewardPunishPayrollRequest){
        return rewardPunishService.calculateSalary(rewardPunishPayrollRequest);
    }

    @PostMapping
    private  ResponseEntity<?> addRewardPunish(@RequestBody RewardPunishResponse rewardPunishResponse){
        return rewardPunishService.addRewardPunish(rewardPunishResponse);
    }

    @DeleteMapping
    private  ResponseEntity<?>deleteRewardPunish(@RequestBody RewardPunishResponse rewardPunishResponse){
        return rewardPunishService.deleteRewardPunish(rewardPunishResponse);
    }


}
