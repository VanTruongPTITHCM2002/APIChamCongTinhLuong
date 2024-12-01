package com.chamcongtinhluong.employee.utils;

import com.chamcongtinhluong.employee.entity.Departments;
import com.chamcongtinhluong.employee.repository.DepartmentsRepository;
import com.chamcongtinhluong.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GenerateID {
    private final EmployeeRepository employeeRepository;
    private final DepartmentsRepository departmentsRepository;
//    private static final Map<String, String> departmentCodes = Map.of(
//            "Kế toán", "KT",
//            "Nhân sự", "NS",
//            "Hành chính", "HC",
//            "Kỹ thuật","IT"
//    );
    public String generateIdEmoloyee(String departmentName){
        Departments departments = departmentsRepository.findByDepartmentName(departmentName);
//        String departmentCode = departmentCodes.get(departmentName);
        String[] words = departmentName.split(" "); // Tách tên phòng ban thành các từ
        StringBuilder departmentCode = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                departmentCode.append(word.charAt(0)); // Lấy chữ cái đầu tiên của mỗi từ
            }
        }

        String departmentCodeUpper = departmentCode.toString().toUpperCase();
        if (departments == null) {
            throw new IllegalArgumentException("Phòng ban không hợp lệ");
        }
        int nextSequence = getNextSequenceForDepartment(departmentName);
        String formattedSequence = String.format("%02d", nextSequence);
        return "NV" + departmentCodeUpper + formattedSequence;
    }
    private int getNextSequenceForDepartment(String department) {
        // Đếm số lượng nhân viên trong phòng ban và tăng số thứ tự lên 1
        return employeeRepository.countByDepartments(department) + 1;
    }
}
