package com.chamcongtinhluong.attendence.utils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class convertTime {
    public static String formatTime(Time time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(time);
    }

    public static Time parseTime(String timeString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        long ms = sdf.parse(timeString).getTime();
        return new Time(ms);
    }
}
