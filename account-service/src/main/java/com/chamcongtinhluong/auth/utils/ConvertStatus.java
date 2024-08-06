package com.chamcongtinhluong.auth.utils;

import org.springframework.stereotype.Component;

@Component
public class ConvertStatus {
    public String convert(int number) {
        String status = "";
        switch (number) {
            case 0:
                status = "Ngưng hoạt động";
                break;
            case 1:
                status = "Đang hoạt động";
                break;
            default:
                status = "";

        }
        return status;
    }

    public int convert(String str) {
        int number = 0;
        switch (str) {
            case "Ngưng hoạt động":
                number = 0;
                break;
            case "Đang hoạt động":
                number = 1;
                break;
            default:
                number = -1;

        }
        return number;
    }
}
