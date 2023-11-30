package com.team15.erp.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public abstract class Mapper<T> {
    public final String NULL = "NULL";
    private static final String DEFAULT_DATE_TIME = "0001-01-01 00:00:00";
    protected DBConnection dbConnection = DBConnection.getInstance();

    protected abstract T map(final Object[] row, final String[] columnNames);

    protected ZonedDateTime toZonedDateTime(String datetimeString) {
        String inputDateTime = datetimeString;
        if (inputDateTime.equals("null")) {
            inputDateTime = DEFAULT_DATE_TIME;
        }
        TimeZone tzSeoul = TimeZone.getTimeZone("Asia/Seoul");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(tzSeoul.toZoneId());
        return ZonedDateTime.parse(inputDateTime, dateTimeFormatter);
    }

    protected String getCurrentZonedDateTimeToString() { //현재 시간 계산
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(ZonedDateTime.now());
    }
}
