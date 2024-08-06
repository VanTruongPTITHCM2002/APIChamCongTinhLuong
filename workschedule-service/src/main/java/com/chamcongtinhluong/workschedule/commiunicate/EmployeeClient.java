package com.chamcongtinhluong.workschedule.commiunicate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeClient {
    private String idemployee;

    private String firstname;

    private String lastname;


}
