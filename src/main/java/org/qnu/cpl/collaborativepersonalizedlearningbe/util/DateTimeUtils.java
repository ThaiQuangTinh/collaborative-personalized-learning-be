package org.qnu.cpl.collaborativepersonalizedlearningbe.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private DateTimeUtils() {
        // prevent instantiate
    }

    public static String formatEndOfDay(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }

        LocalDateTime endOfDay = LocalDateTime.of(
                dateTime.toLocalDate(),
                LocalTime.of(23, 59)
        );

        return endOfDay.format(DATE_TIME_FORMATTER);
    }
}
