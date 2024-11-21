package com.chamcongtinhluong.account_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {
    private String rolename;
    private String roleDescription;
    private String createAt;
    private String updateAt;
    private String scope;
    private Boolean isActive;
}
