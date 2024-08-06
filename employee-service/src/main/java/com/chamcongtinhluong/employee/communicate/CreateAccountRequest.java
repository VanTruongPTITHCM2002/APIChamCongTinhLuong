package com.chamcongtinhluong.employee.communicate;

import java.util.Date;

public class CreateAccountRequest {
    private String idemployee;
    private String password;
    private int role;

    public CreateAccountRequest() {
    }

    public CreateAccountRequest(String idemployee, String password, int role) {
        this.idemployee = idemployee;
        this.password = password;
        this.role = role;
    }

    public String getIdemployee() {
        return idemployee;
    }

    public void setIdemployee(String idemployee) {
        this.idemployee = idemployee;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
