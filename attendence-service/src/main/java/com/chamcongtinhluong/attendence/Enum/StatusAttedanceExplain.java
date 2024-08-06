package com.chamcongtinhluong.attendence.Enum;

public enum StatusAttedanceExplain {
    DANG_CHO_DUYET("Đang chờ duyệt", 0),
    DUYET("Duyệt", 1),
    KHONG_DUYET("Không duyệt", -1);

    private final String status;
    private final int code;

    StatusAttedanceExplain(String status, int code) {
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
        for (StatusAttedanceExplain s : StatusAttedanceExplain.values()) {
            if (s.status.equalsIgnoreCase(status)) {
                return s.code;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }

    public static String getStatusFromCode(int code){
        for (StatusAttedanceExplain s : StatusAttedanceExplain.values()) {
            if (s.code == code) {
                return s.status;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
