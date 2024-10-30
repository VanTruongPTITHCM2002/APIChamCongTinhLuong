package com.chamcongtinhluong.employee.Enum;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EmployeeStatus {
    @JsonProperty("active")
    ACTIVE,
    @JsonProperty("inactive")
    INACTIVE,
    @JsonProperty("none")
    NONE
}
