package com.chamcongtinhluong.account_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequest {
    @NotNull(message = "Khong hop le vi thieu ten tai khoan")
    @NotBlank(message = "Vui long nhap tai khoan")
    @Pattern(regexp = "^\\S+$", message = "Ten tai khoan khong duoc chua khoang trang")
    private String username;

    @NotNull(message = "Khong hop le vi thieu mat khau")
    @NotBlank(message = "Vui long nhap mat khau")
    @Pattern(regexp = "^\\S+$", message = "Mat khau khong duoc chua khoang trang")
    private String password;

}
