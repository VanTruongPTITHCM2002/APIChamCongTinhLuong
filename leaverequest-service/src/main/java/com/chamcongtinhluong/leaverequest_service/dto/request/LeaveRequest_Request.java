package com.chamcongtinhluong.leaverequest_service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class LeaveRequest_Request {
    @NotNull(message = "Vui long khong bo qua ma nhan vien")
    @NotEmpty(message = "Vui long khong bo trong ma nhan vien")
    private String idEmployee;
    private String leaveType;
    private Date startDate;
    private Date endate;
    private String status;
    private String reason;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private String createAt;
    private String approveBy;
    private String approveAt;
}
