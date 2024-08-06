package com.chamcongtinhluong.employee.Enum;

public enum DegreeNumber {
   DAI_HOC("Đại học", 1),
   CAO_DANG("Cao đẳng", 2),
   THAC_SI("Thạc sĩ", 3),
    TIEN_SI("Tiến sĩ", 4);

    private final String status;
    private final int code;

    DegreeNumber(String status, int code) {
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
        for (DegreeNumber s : DegreeNumber.values()) {
            if (s.status.equalsIgnoreCase(status)) {
                return s.code;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }

    public static String getStatusFromCode(int code) {
        for (DegreeNumber s : DegreeNumber.values()) {
            if (s.code == code) {
                return s.status;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
