package com.chamcongtinhluong.employee.dto.request;

import com.chamcongtinhluong.employee.validation.Adult;
import com.chamcongtinhluong.employee.validation.Unique;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {
    @NotNull(message = "Khong hop le vi thieu ma nhan vien")
    @NotBlank(message = "Vui long nhap ma nhan vien")
    @Pattern(regexp = "^\\S+$", message = "Ma nhan vien khong duoc chua khoang trang")
    private String idEmployee;
    private String firstName;
    @NotNull(message = "Khong hop le vi thieu ten nhan vien")
    @NotBlank(message = "Vui long nhap ten nhan vien")
    private String lastName;
    private String gender;
    @Adult
    private Date birthDate;
    @Unique(fieldName = "idCard", message = "Căn cước này đã tồn tại trong ứng dụng")
    private String idCard;
    @Unique(fieldName = "email",message = "Email này đã tồn tại trong ứng dụng")
    @Email(message = "Không đúng định dạng email")
    private String email;
    @Unique(fieldName = "phoneNumber",message = "Số điện thoại này đã tồn tại trong ứng dụng")
    private String phoneNumber;
    private String address;
    private String degree;
    private String position;
    private String department;
    private String status;
    private byte[] image;
}
