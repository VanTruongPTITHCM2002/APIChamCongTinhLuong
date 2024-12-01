package com.chamcongtinhluong.leaverequest_service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeaveRequest_Response {
    private String idEmployee;
    private String leaveType;
    private Date startDate;
    private Date endate;
    private String status;
    private String reason;
    private String createAt;
    private String approveBy;
    private String approveAt;
}
