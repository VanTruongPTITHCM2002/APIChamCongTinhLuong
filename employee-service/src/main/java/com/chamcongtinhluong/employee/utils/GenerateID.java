package com.chamcongtinhluong.employee.utils;

import com.chamcongtinhluong.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class GenerateID {
    private final EmployeeRepository employeeRepository;

    private static final Map<String, String> departmentCodes = Map.of(
            "Kế toán", "KT",
            "Nhân sự", "NS",
            "Hành chính", "HC",
            "Phòng IT","IT"
    );
    public String generateIdEmoloyee(String departmentName){
        String departmentCode = departmentCodes.get(departmentName);
        if (departmentCode == null) {
            throw new IllegalArgumentException("Phòng ban không hợp lệ");
        }
        int nextSequence = getNextSequenceForDepartment(departmentName);
        String formattedSequence = String.format("%02d", nextSequence);
        return "NV" + departmentCode + formattedSequence;
    }
    private int getNextSequenceForDepartment(String department) {
        // Đếm số lượng nhân viên trong phòng ban và tăng số thứ tự lên 1
        return employeeRepository.countByDepartments(department) + 1;
    }
}
