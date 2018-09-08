package com.codequest.kitchan.countdowntimer.utils;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class StringUtils {

    public static final SimpleDateFormat date_fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static String hmsTimeFormatter(long milliSeconds) {

        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return hms;
    }

    public static String longToStr(Long l, SimpleDateFormat format) {
        return format.format(l);
    }
}
