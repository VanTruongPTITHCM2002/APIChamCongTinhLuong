package com.chamcongtinhluong.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissonsRequest {
    @NotNull(message = "Vui long khong bo trong ten")
    @NotBlank(message = "Vui long nhap ten")
    private String namePermissons;
    private String description;
    private Date createAt;
    private Date updateAt;
}
