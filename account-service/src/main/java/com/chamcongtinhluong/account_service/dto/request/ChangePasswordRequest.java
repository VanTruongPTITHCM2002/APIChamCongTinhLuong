package com.chamcongtinhluong.account_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordRequest {

    @NotNull(message = "Khong hop le vi thieu ten tai khoan")
    @NotBlank(message = "Vui long nhap tai khoan")
    @Pattern(regexp = "^\\S+$", message = "Ten tai khoan khong duoc chua khoang trang")
    private String username;

    @NotNull(message = "Khong hop le vi thieu mat khau cu")
    @NotBlank(message = "Vui long nhap mat khau cu")
    @Pattern(regexp = "^\\S+$", message = "Mat khau cu khong duoc chua khoang trang")
    private String oldpassword;

    @NotNull(message = "Khong hop le vi thieu mat khau moi")
    @NotBlank(message = "Vui long nhap mat khau moi")
    @Pattern(regexp = "^\\S+$", message = "Mat khau moi khong duoc chua khoang trang")
    private String newpassword;
}
