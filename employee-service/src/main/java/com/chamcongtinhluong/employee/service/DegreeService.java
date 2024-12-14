package com.chamcongtinhluong.employee.service;

import com.chamcongtinhluong.employee.dto.request.DegreeRequest;
import org.springframework.http.ResponseEntity;

public interface DegreeService {
    ResponseEntity<?> getDegree();
    ResponseEntity<?> addDegree(DegreeRequest degreeRequest);
    ResponseEntity<?> updateDegree(String degreeName,DegreeRequest degreeRequest);
    ResponseEntity<?> deleteDegree(String degreeName);
}
