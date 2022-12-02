package com.asset.reservation.asset.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtils {

  private static final int FIXED_PERIOD_FOR_RESERVATION = 365;
  private static final String DATE_FORMAT = "dd-MM-yy";

  public static Date parse(String date) throws ParseException {
    DateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
    simpleDateFormat.setLenient(false);
    return simpleDateFormat.parse(date);
  }

  public static boolean isDateIntervalValid(LocalDateTime date1, LocalDateTime date2) {
    long daysBetween = computeDaysBetween(date1, date2);
    return !(daysBetween < 1)
        && !date1.isBefore(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT))
        && daysBetween <= FIXED_PERIOD_FOR_RESERVATION;
  }

  public static long computeDaysBetween(LocalDateTime startDate, LocalDateTime endDate) {
    return ChronoUnit.DAYS.between(startDate, endDate);
  }

  public static LocalDateTime dateToLocalDateTime(Date date) {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

  public static Date localDateTimeToDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }
}
