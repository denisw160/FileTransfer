package de.netpage.filetransfer.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class helps to convert {@link java.util.Date}.
 */
public class DateUtils {

    private DateUtils() {
        // only static methods
    }

    /**
     * Converts the date to a timestamp in String format.
     *
     * @param date Date
     * @return Timestamp
     */
    public static String convertTimestamp(final Date date) {
        if (date == null) {
            return null;
        }

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return sdf.format(date);
    }

}
