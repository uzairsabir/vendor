package org.vanguardmatrix.engine.parser;

import android.util.Log;



import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseDate {

    public static String FORMAT_yyyyMMdd = "yyyy-MM-dd";
    public static String FORMAT_ddMMYYYY = "dd MM yyyy";
    public static String FORMAT_yyyyMMdd_ddd = "yyyy-MM-dd-EEEE";
    public static String FORMAT_YYYY_MM_DD_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static String FORMAT_HH_mm_yyyyMMdd_ddd = "HH:mm dd-MMM-yyyy";

    public static String FORMAT_STANDARD = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    // 2015-01-05 13:37:04

    public static Date getDateFromString(String myString, String myFormat) {

        SimpleDateFormat format;
        try {
            format = new SimpleDateFormat(myFormat);
            return (Date) format.parse(myString);
        } catch (ParseException e) {
            // e.printStackTrace();
            return null;
        }

    }

    public static String getStringFromDate(Date date, String myFormat) {

        SimpleDateFormat format;
        format = new SimpleDateFormat(myFormat);
        return format.format(date);

    }

    public static int getTodayInt(Date date) {
        try {

            SimpleDateFormat format;
            format = new SimpleDateFormat(FORMAT_yyyyMMdd_ddd);
            String day = format.format(date);
            Log.e("dat", " : " + day.split("-")[3]);

            return UtilityFunctions.getDayIntFromString(day.split("-")[3]);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static String getTodaysName(Date date) {
        try {

            SimpleDateFormat format;
            format = new SimpleDateFormat(FORMAT_yyyyMMdd_ddd);
            String day = format.format(date);
            Log.e("dat", " : " + day.split("-")[3]);

            return day.split("-")[3];

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "MON";

    }

    public static int daysBw2dates(Date date1, Date date2) {

        long diff = date2.getTime() - date1.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        Log.e("aw", "return days " + days + " for date1: " + date1.toString()
                + " and date2 : " + date2.toString());

        if (0 < hours && hours < 24)
            return 1;
        if (24 < hours && hours < 48)
            return 2;
        if (48 < hours && hours < 72)
            return 3;
        if (72 < hours && hours < 96)
            return 4;
        if (96 < hours && hours < 120)
            return 5;

        return (int) days;

    }

    public static Date getDateFromLongSystemTime(long myString) {

        try {
            return new Date(myString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

//    public static int getDayDifferenceBwTimes(Time t1, Time t2) {
//        long diff = t1.convertStandardDate().getTime() - t2.convertStandardDate().getTime();
//        long seconds = diff / 1000;
//        long minutes = seconds / 60;
//        long hours = minutes / 60;
//        long days = hours / 24;
//        return (int) days;
//    }

}
