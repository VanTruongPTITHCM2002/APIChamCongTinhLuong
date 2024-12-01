package com.chamcongtinhluong.notification_service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationRequest {
    @NotNull(message = "Vui long khong bo qua nguoi gui")
    @NotEmpty(message = "Vui long khong de trong nguoi gui")
    private String senderId;
    @NotNull(message = "Vui long khong bo qua nguoi nhan")
    @NotEmpty(message = "Vui long khong de trong nguoi nhan")
    private String receiverId;
    @NotNull(message = "Vui long khong bo qua noi dung")
    @NotEmpty(message = "Vui long khong de trong noi dung")
    private String content;
    @NotNull(message = "Vui long khong bo qua chu de thong bao")
    @NotEmpty(message = "Vui long khong de trong chu de thong bao")
    private String type;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date createAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date updateAt;
}
