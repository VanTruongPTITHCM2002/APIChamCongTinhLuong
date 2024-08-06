package com.chamcongtinhluong.contract_service.Enum;

public enum StatusContract {
    CON_HOP_DONG("Còn hợp đồng",1),
   HET_HOP_DONG("Hết hợp đồng",-1);

    private  final String status;
    private  final int code;

    StatusContract( String status, int code) {
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
        for (StatusContract s : StatusContract.values()) {
            if (s.status.equalsIgnoreCase(status)) {
                return s.code;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }

    public static String getStatusFromCode(int code) {
        for (StatusContract s : StatusContract.values()) {
            if (s.code == code) {
                return s.status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + code);
    }
}
