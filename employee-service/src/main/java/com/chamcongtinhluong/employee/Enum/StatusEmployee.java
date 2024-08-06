package com.chamcongtinhluong.employee.Enum;

public enum StatusEmployee {
    DANG_HOAT_DONG("Đang hoạt động", 1),
    NGUNG_HOAT_DONG("Ngưng hoạt động", 0);
    private final String status;
    private final int code;

    StatusEmployee(String status, int code) {
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
        for (StatusEmployee s : StatusEmployee.values()) {
            if (s.status.equalsIgnoreCase(status)) {
                return s.code;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }

    public static String getStatusFromCode(int code) {
        for (StatusEmployee s : StatusEmployee.values()) {
            if (s.code == code) {
                return s.status;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
