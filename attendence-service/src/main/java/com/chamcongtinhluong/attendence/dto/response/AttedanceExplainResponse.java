package com.chamcongtinhluong.attendence.dto.response;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttedanceExplainResponse {
    private String idemployee;
    @Temporal(TemporalType.DATE)
    private Date date;
    @Temporal(TemporalType.TIME)
    private Time checkintime;
    @Temporal(TemporalType.TIME)
    private Time checkoutime;
    private String explaination;
    private String status;
}
