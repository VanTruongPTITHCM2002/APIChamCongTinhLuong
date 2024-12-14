package com.chamcongtinhluong.employee.service.impl;

import com.chamcongtinhluong.employee.dto.request.DegreeRequest;
import com.chamcongtinhluong.employee.dto.response.ResponeObject;
import com.chamcongtinhluong.employee.entity.Degree;
import com.chamcongtinhluong.employee.repository.DegreeRepository;
import com.chamcongtinhluong.employee.service.DegreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DegreeServiceImpl implements DegreeService {

    private final DegreeRepository degreeRepository;


    @Override
    public ResponseEntity<?> getDegree() {
        List<DegreeRequest> degreeRequestList =
                degreeRepository.findAll().stream().map(
                        degree -> DegreeRequest.builder()
                                .degreeName(degree.getDegreename())
                                .numberSalary(degree.getNumber_sal())
                                .build()
                ).toList();
        return ResponseEntity.ok().body(ResponeObject.builder()
                        .status(HttpStatus.OK.value())
                        .message("Lay du lieu thanh cong")
                        .data(degreeRequestList)
                .build());
    }

    @Override
    public ResponseEntity<?> addDegree(DegreeRequest degreeRequest) {
        Degree degree = degreeRepository.findByDegreename(degreeRequest.getDegreeName());
        if(degree != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponeObject.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Bằng cấp đã tồn tại")
                            .build());
        }
        degree = new Degree();
        degree.setDegreename(degreeRequest.getDegreeName());
        degree.setNumber_sal(degreeRequest.getNumberSalary());
        degreeRepository.save(degree);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponeObject.builder()
                .status(HttpStatus.CREATED.value())
                .message("Them bang cap thanh cong")
                .build());
    }

    @Override
    public ResponseEntity<?> updateDegree(String degreeName,DegreeRequest degreeRequest) {
        Degree degree = degreeRepository.findByDegreename(degreeName);
        if(degree == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponeObject.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("Khong tim thay bang cap")
                            .build());
        }
        degree.setDegreename(degreeRequest.getDegreeName());
        degree.setNumber_sal(degreeRequest.getNumberSalary());
        degreeRepository.save(degree);
        return ResponseEntity.ok().body(ResponeObject.builder()
                .status(HttpStatus.OK.value())
                .message("Sua bang cap thanh cong")
                .build());
    }

    @Override
    public ResponseEntity<?> deleteDegree(String degreeName) {
        Degree degree = degreeRepository.findByDegreename(degreeName);
        if(degree == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponeObject.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("Khong tim thay bang cap")
                            .build());
        }
        if(!degree.getEmployeeList().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponeObject.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Khong the xoa bang cap nay")
                            .build());
        }
        degreeRepository.delete(degree);
        return ResponseEntity.ok().body(ResponeObject.builder()
                .status(HttpStatus.OK.value())
                .message("Xoa bang cap thanh cong")
                .build());
    }


}
