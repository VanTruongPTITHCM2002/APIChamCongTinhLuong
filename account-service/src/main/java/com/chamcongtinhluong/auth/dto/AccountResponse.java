package com.chamcongtinhluong.auth.dto;

public class AccountResponse {
    private String username;
    private String role;
    private String status;
    public AccountResponse() {
    }

    public AccountResponse(String username, String role, String status) {
        this.username = username;
        this.role = role;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
