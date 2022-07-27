package com.hindbyte.tradingchart.klinelib.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtils {

    public static String formatDateTime(long time, String format) {
        DateFormat dateFormat2 = new SimpleDateFormat(format, Locale.getDefault());
        String formatDate = dateFormat2.format(time);
        return formatDate;
    }


    public static String formatDate(long time) {
        DateFormat dateFormat2 = new SimpleDateFormat("MM/dd", Locale.getDefault());
        String formatDate = dateFormat2.format(time);
        return formatDate;
    }

    public static String formatTime(long time) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String formatDate = dateFormat.format(time);
        return formatDate;
    }


}
