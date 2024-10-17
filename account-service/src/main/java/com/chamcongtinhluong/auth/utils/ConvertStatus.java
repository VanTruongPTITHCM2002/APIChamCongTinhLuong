package com.chamcongtinhluong.auth.utils;

import org.springframework.stereotype.Component;

@Component
public class ConvertStatus {
    public String convert(int number) {
        return switch (number) {
            case 0 -> "Ngưng hoạt động";
            case 1 -> "Đang hoạt động";
            default -> "";
        };
    }

    public int convert(String str) {
        return switch (str) {
            case "Ngưng hoạt động" -> 0;
            case "Đang hoạt động" -> 1;
            default -> -1;
        };
    }

}
