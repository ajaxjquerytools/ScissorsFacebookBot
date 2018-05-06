package com.core.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class DateTimeUtil {
    private static final String FORMAT_PA_SMALL = "yyyyMMdd'T'HHmmZ";
    private static final String FORMAT_PA = "yyyyMMdd'T'HHmmssZ";
    private static final String FORMAT_LS = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String FORMAT_LS_19 = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String FORMAT_LS_23 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final String DATE_FORMAT = "yyyyMMdd";
    private static final String TIME_FORMAT = "HHmmssSSS";
    private static final String EVENT_TIME_FORMAT = "HHmm";
    private static final String PLAYER_TIME_FORMAT = "HHmmss";
    private static final String FORMAT_WITH_NANOS = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    private static final String FORMAT_TIME = "yyyyMMdd'T'HHmmZ";
    public static final String FORMAT_H_M_S = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String FORMAT_H_M_S_2 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_RACING_AUSTRALIA_HORSES = "yyyy-MM-dd'T'HH:mm:ssXXX";

    private static final String TIME_FORMAT_RACING_AUSTRALIA = "HH:mm:ss.S";

    private static final DateTimeFormatter DF_STRING_FORMAT_FULL = DateTimeFormatter.ofPattern(FORMAT_TIME).withZone(ZoneOffset.UTC);

    private static final DateTimeFormatter DF_DATE_FORMAT = DateTimeFormatter.ofPattern(DATE_FORMAT).withZone(ZoneOffset.UTC);
    private static final DateTimeFormatter DF_TIME_FORMAT = DateTimeFormatter.ofPattern(TIME_FORMAT).withZone(ZoneOffset.UTC);
    private static final DateTimeFormatter DF_EVENT_TIME_FORMAT = DateTimeFormatter.ofPattern(EVENT_TIME_FORMAT).withZone(ZoneOffset.UTC);
    private static final DateTimeFormatter DF_PLAYER_TIME_FORMAT = DateTimeFormatter.ofPattern(PLAYER_TIME_FORMAT).withZone(ZoneOffset.UTC);

    public static String getFormattedString(Long time) {
        if (time == null || time == 0) {
            return null;
        }
        return DF_STRING_FORMAT_FULL.format(Instant.ofEpochMilli(time).atZone(ZoneOffset.UTC));
    }

    public static String getEventDate(Long startTime) {
        if (startTime == null || startTime == 0) {
            return null;
        }
        return DF_DATE_FORMAT.format(Instant.ofEpochMilli(startTime).atZone(ZoneOffset.UTC));
    }

    public static String getTimeNoSeconds(Long startTime) {
        if (startTime == null || startTime == 0) {
            return null;
        }
        return DF_EVENT_TIME_FORMAT.format(Instant.ofEpochMilli(startTime).atZone(ZoneOffset.UTC));
    }

    public static String getTimeWithSeconds(Long startTime) {
        if (startTime == null || startTime == 0) {
            return null;
        }
        return DF_PLAYER_TIME_FORMAT.format(Instant.ofEpochMilli(startTime).atZone(ZoneOffset.UTC));
    }

    public static Long getEndOfTheDayTimeStamp() {
        LocalDate date = ZonedDateTime.now(ZoneOffset.UTC).toLocalDate();
        LocalDateTime dt = date.atStartOfDay().plusDays(1).minusSeconds(1);
        return dt.toInstant(ZoneOffset.UTC).toEpochMilli();
    }


    public static Long nextDayInSeconds(long timestampInMillis) {
        long nextTimestamp = timestampInMillis + 24 * 60 * 60 * 1000;
        return TimeUnit.MILLISECONDS.toSeconds(nextTimestamp);
    }

    public static boolean hasExpired(long timestampInSeconds) {
        long timestampInMillis = TimeUnit.SECONDS.toMillis(timestampInSeconds);
        return timestampInMillis < System.currentTimeMillis();
    }
}
