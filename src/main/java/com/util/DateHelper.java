package com.util;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateHelper {
    private static final ZoneId DEFAULT_TIME_ZONE_EUROPE_MEZ = ZoneId.of("Europe/Berlin");
    private static final Locale DEFAULT_TIME_ZONE_LOCALE;

    private static Calendar getMEZCalendarInstance() {
        return Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_TIME_ZONE_EUROPE_MEZ), DEFAULT_TIME_ZONE_LOCALE);
    }

    public static Date getDate(int day, int month, int year) {
        assert month >= 0 && month <= 11 : "Month must be in the interval [0,11]";

        Calendar cal = getMEZCalendarInstance();
        cal.set(5, day);
        cal.set(2, month);
        cal.set(1, year);
        cal.set(10, 11);
        cal.set(12, 11);
        cal.set(13, 11);
        cal.set(14, 11);
        return cal.getTime();
    }

    public static int compareDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int jahr1 = cal1.get(1);
        int jahr2 = cal2.get(1);
        int result = jahr1 < jahr2 ? -1 : (jahr1 == jahr2 ? 0 : 1);
        if (result == 0) {
            int monat1 = cal1.get(2);
            int monat2 = cal2.get(2);
            result = monat1 < monat2 ? -1 : (monat1 == monat2 ? 0 : 1);
        }

        if (result == 0) {
            int tag1 = cal1.get(5);
            int tag2 = cal2.get(5);
            result = tag1 < tag2 ? -1 : (tag1 == tag2 ? 0 : 1);
        }

        return result;
    }

    public static java.sql.Date convertDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static Date convertDate(java.sql.Date date) {
        return new Date(date.getTime());
    }

    static {
        DEFAULT_TIME_ZONE_LOCALE = Locale.GERMANY;
    }
}
