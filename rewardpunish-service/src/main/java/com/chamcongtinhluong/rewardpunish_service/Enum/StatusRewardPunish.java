package com.chamcongtinhluong.rewardpunish_service.Enum;

public enum StatusRewardPunish {
    TON_TAI("Tồn tại",1),
    DA_XOA("Đã xóa",-1);

    private  final String status;
    private  final int code;

    StatusRewardPunish( String status, int code) {
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
        for (StatusRewardPunish s : StatusRewardPunish.values()) {
            if (s.status.equalsIgnoreCase(status)) {
                return s.code;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }

    public static String getStatusFromCode(int code) {
        for (StatusRewardPunish s : StatusRewardPunish.values()) {
            if (s.code == code) {
                return s.status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + code);
    }
}
