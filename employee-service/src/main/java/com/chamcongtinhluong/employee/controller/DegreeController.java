package com.chamcongtinhluong.employee.controller;

import com.chamcongtinhluong.employee.dto.request.DegreeRequest;
import com.chamcongtinhluong.employee.service.DegreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/degree")
@RequiredArgsConstructor
public class DegreeController {
    private final DegreeService degreeService;

    @GetMapping
    private ResponseEntity<?> getDegree(){
        return degreeService.getDegree();
    }

    @PostMapping
    private ResponseEntity<?> addDegree(@RequestBody DegreeRequest degreeRequest){
        return degreeService.addDegree(degreeRequest);
    }

    @PutMapping
    private ResponseEntity<?> updateDegree(@RequestParam String degreeName,@RequestBody DegreeRequest degreeRequest){
        return degreeService.updateDegree(degreeName,degreeRequest);
    }

    @DeleteMapping
    private ResponseEntity<?> deleteDegree(@RequestParam String degreeName){
        return  degreeService.deleteDegree(degreeName);
    }
}
