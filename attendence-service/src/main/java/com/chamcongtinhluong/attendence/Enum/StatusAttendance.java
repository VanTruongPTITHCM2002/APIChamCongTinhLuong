package com.chamcongtinhluong.attendence.Enum;

public enum StatusAttendance {
    DI_TRE("Đi trễ", 1),
    VE_SOM("Về sớm", 2),
    DI_SOM("Đi sớm", 3),
    NGHI_PHEP("Nghỉ phép", 4),
    KHONG_PHEP("Không phép", 5),
    NGAY_NGHI("Ngày nghỉ", 6),
    DI_LAM_DAY_DU("Đi làm đầy đủ", 7),
    NGHI_BU("Nghỉ bù", 8),
    LAM_THEM_GIO("Làm thêm giờ", 9),
    CONG_TAC("Công tác", 10),
    DI_TRE_VE_SOM("Đi trễ về sớm",11),
    KHONG_DI_LAM("Không đi làm",12);
    private final String status;
    private final int code;

    StatusAttendance(String status, int code) {
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
        for (StatusAttendance s : StatusAttendance.values()) {
            if (s.status.equalsIgnoreCase(status)) {
                return s.code;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}
