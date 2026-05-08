package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final SimpleDateFormat DATE_FORMAT =
        new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DATETIME_FORMAT =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String formatDate(long millis) {
        return DATE_FORMAT.format(new Date(millis));
    }

    public static String formatDateTime(long millis) {
        return DATETIME_FORMAT.format(new Date(millis));
    }

    public static int daysBetween(long startMillis, long endMillis) {
        long diff = endMillis - startMillis;
        return (int) (diff / (1000 * 60 * 60 * 24));
    }

    public static String getCurrentDateTime() {
        return DATETIME_FORMAT.format(new Date());
    }
}