package com.chamcongtinhluong.leaverequest_service.Enum;

public enum Status {
    PENDING("Đang chờ"),
    APPROVED("Chấp thuận"),
    REJECTED("Không chấp thuận");
    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Status convertStringToEnum(String value) {
        for (Status status : Status.values()) {
            if (status.getDisplayName().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching LeaveType for value: " + value);
    }
}
