package com.chamcongtinhluong.rewardpunish_service.Enum;

public enum TypeRewardPunish {
    THUONG("Thưởng",1),
    PHAT("Phạt",-1);

    private  final String status;
    private  final int code;

    TypeRewardPunish( String status, int code) {
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
        for (TypeRewardPunish s : TypeRewardPunish.values()) {
            if (s.status.equalsIgnoreCase(status)) {
                return s.code;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }

    public static String getStatusFromCode(int code) {
        for (TypeRewardPunish s : TypeRewardPunish.values()) {
            if (s.code == code) {
                return s.status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + code);
    }
}
