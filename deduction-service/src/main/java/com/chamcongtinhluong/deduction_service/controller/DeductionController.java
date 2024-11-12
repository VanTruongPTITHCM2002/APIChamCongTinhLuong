package com.chamcongtinhluong.deduction_service.controller;

import com.chamcongtinhluong.deduction_service.service.DeductionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/deduction")
@RequiredArgsConstructor
public class DeductionController {
    private final DeductionService deductionService;
}
