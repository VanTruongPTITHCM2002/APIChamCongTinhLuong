package com.chamcongtinhluong.employee.validation.validator;

import com.chamcongtinhluong.employee.repository.EmployeeRepository;
import com.chamcongtinhluong.employee.validation.Unique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueValidator implements ConstraintValidator<Unique,String> {

    private final EmployeeRepository employeeRepository;
    private String fieldName;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null || s.isEmpty()){
            return true;
        }
        boolean exists = switch (fieldName) {
            case "idCard" -> employeeRepository.existsByCmnd(s);
            case "email" -> employeeRepository.existsByEmail(s);
            case "phoneNumber" -> employeeRepository.existsByPhonenumber(s);
            default -> throw new IllegalArgumentException("Unknown field: " + fieldName);
        };

        return !exists; // Trả về true nếu không tồn tại bản ghi nào
    }
}
