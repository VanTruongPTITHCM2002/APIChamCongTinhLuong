package com.chamcongtinhluong.payroll_service.Enum;

public enum StatusPayroll {
    DA_THANH_TOAN("Đã thanh toán",1),
    CHUA_THANH_TOAN("Chưa thanh toán",0);

    private final String status;
    private final int code;

    StatusPayroll(String status, int code) {
        this.status = status;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public static int getCodeFromStatus(String status) {
        for (StatusPayroll s : StatusPayroll.values()) {
            if (s.status.equalsIgnoreCase(status)) {
                return s.code;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }

    public static String getStatusFromCode(int code) {
        for (StatusPayroll s : StatusPayroll.values()) {
            if (s.code == code) {
                return s.status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + code);
    }
}
