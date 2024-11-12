package com.chamcongtinhluong.employee.validation;

import com.chamcongtinhluong.employee.validation.validator.AdultVallidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AdultVallidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Adult {
    String message() default  "Vui lòng đủ 18 tuổi trở lên";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
