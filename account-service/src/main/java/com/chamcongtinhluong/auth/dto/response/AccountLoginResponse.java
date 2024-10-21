package com.chamcongtinhluong.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountLoginResponse {
    private String username;
    private String token;
    private String role;
    private int status;
}
