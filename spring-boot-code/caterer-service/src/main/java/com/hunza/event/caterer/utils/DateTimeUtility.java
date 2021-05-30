package com.hunza.event.caterer.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Class {@link DateTimeUtility} is a date time utils.
 * <p>Is is responsible for date time conversion and it's operations</p>
 */
public class DateTimeUtility {

    /**
     * Method generates a date and time with specific format.
     * <p>It will be responsible to generate a date time with UTC timezone</p>
     *
     * @return {@link String}
     */
    public static String dateTimeForGlobalException() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(new Date());
    }
}
