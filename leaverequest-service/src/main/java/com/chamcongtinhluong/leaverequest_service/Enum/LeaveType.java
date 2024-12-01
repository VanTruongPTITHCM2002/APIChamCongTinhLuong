package com.chamcongtinhluong.leaverequest_service.Enum;


public enum LeaveType {
    PAID("Nghỉ phép"),
    UNPAID("Nghỉ hết phép"),
    SICK("Nghỉ bệnh"),
    MATERNITY("Nghỉ thai sản"),
    EMERGENCY("Nghỉ khẩn cấp");

    private final String displayName;

    LeaveType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static LeaveType convertStringToEnum(String value) {
        for (LeaveType type : LeaveType.values()) {
            if (type.getDisplayName().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No matching LeaveType for value: " + value);
    }
}
