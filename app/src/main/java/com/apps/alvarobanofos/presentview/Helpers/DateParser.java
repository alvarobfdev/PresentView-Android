package com.apps.alvarobanofos.presentview.Helpers;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by alvarobanofos on 19/1/16.
 */
public class DateParser {

    private static final String TAG = "DateParser";
    public static final String DEFAULT_SQL_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String parseDate(String inputPattern, String outputPattern, String time) {

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        inputFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        outputFormat.setTimeZone(TimeZone.getDefault());

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static long getTimeFromString(String time, String pattern) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(pattern);
        inputFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = null;
        long returnTime = 0;
        try {
            date = inputFormat.parse(time);
            returnTime = date.getTime();
        } catch (ParseException e) {
            Log.w(TAG, "Error parsing time");
        }

        return returnTime;
    }

    public static String getStringFromLong(Long time, String outputPattern) {

        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        outputFormat.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        return outputFormat.format(new Date(time));
    }

    public static Date getDateFromString(String time, String inputPattern) {
        SimpleDateFormat format = new SimpleDateFormat(inputPattern);
        try {
            Date date = format.parse(time);
            return date;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static Long now() {
        return System.currentTimeMillis();
    }




}
