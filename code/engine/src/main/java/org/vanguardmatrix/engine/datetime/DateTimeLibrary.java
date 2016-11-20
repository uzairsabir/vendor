package org.vanguardmatrix.engine.datetime;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTimeLibrary {
    public static String getElapsedTime(long startTimeInMilliSeconds) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis()
                - startTimeInMilliSeconds);

        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");

        return formatter.format(calendar.getTime());
    }
}
