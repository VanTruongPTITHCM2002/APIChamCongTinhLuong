package com.chamcongtinhluong.payroll_service.Enum;

public enum StatusSalary {
    ACTIVE("Đang áp dụng",true),
    INACTIVE("Không áp dụng",false);

    private final String status;
    private final boolean code;

    StatusSalary(String status, boolean code) {
        this.status = status;
        this.code = code;
    }




    public static boolean stringToBoolean(String status) {
        for (StatusSalary s : StatusSalary.values()) {
            if (s.status.equalsIgnoreCase(status)) {
                return s.code;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }

    public static String booleanToString (boolean code) {
        for (StatusSalary s : StatusSalary.values()) {
            if (s.code == code) {
                return s.status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + code);
    }

}
